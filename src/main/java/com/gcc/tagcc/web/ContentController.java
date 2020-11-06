package com.gcc.tagcc.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.gcc.tagcc.annotation.PassToken;
import com.gcc.tagcc.annotation.RequestLimit;
import com.gcc.tagcc.annotation.UserLoginToken;
import com.gcc.tagcc.entity.ShareContent;
import com.gcc.tagcc.exception.BaseException;
import com.gcc.tagcc.service.ContentService;
import com.gcc.tagcc.untils.ResultUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author gaocc
 * @create 2019-11-20 17:41
 */
@RestController
@RequestMapping("/share/content/")
public class ContentController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    ContentService contentService;

    /* 最多调用100次，默认是10分钟
    * 限频需要使用HttpServletRequest
    * */
    @RequestLimit(count = 100)
    @RequestMapping("one/add")
    public Object addShareContent(@RequestBody ShareContent shareContent, HttpServletRequest req){
        String userId = getUid(req);
        contentService.addShareContent(shareContent, userId);
        return ResultUtil.success("success");
    }

    @RequestLimit(count = 20)
    @RequestMapping("one/delete")
    public Object deleteShareContent(@RequestBody ShareContent shareContent, HttpServletRequest req){
        contentService.deleteShareContent(shareContent.getId());
        return ResultUtil.success("success");
    }

    @PassToken
    @RequestMapping("query")
    public Object querySelfContent(HttpServletRequest req){
        String userId = getUid(req);
        ArrayList<ShareContent> resp = contentService.querySelfContent(userId);
        return ResultUtil.success(resp);
    }

    @RequestLimit(count = 10)
    @RequestMapping("tourist/add")
    public Object addTourist(@RequestBody ShareContent shareContent, HttpServletRequest req){
        contentService.addTourist(shareContent);
        return ResultUtil.success("success");
    }

    @RequestLimit(count = 20)
    @RequestMapping("one/weight/update")
    public Object upShareContent(@RequestBody ShareContent ret, HttpServletRequest req){
        contentService.upShareContent(ret.getWeight(),ret.getId());
        return ResultUtil.success("success");
    }

    @RequestLimit(count = 3)
    @RequestMapping("upload")
    public Object analysisShareContent(@RequestParam("file") MultipartFile file,HttpServletRequest req){
        String html = "";
        if (file.isEmpty()) {
            throw new BaseException("1003","上传失败，请选择文件");
        }
        try {
            BufferedReader bis = new BufferedReader(new InputStreamReader(file.getInputStream()));
            StringBuilder szContent = new StringBuilder();
            String szTemp;
            while ((szTemp = bis.readLine()) != null) {
                szContent.append(szTemp);
            }
            bis.close();
            html = szContent.toString();
        } catch (Exception e) {
            logger.error("open file fail ===》", e);
            throw new BaseException("1004","文件读取失败");
        }
//        html = "<DT><H3 ITEM_ID=\"{A62AF571-6A95-4BA2-8EDD-92A8BB9743F3}\" LAST_MODIFIED=\"1526282232\" >收藏夹栏</H3>\n" +
//                "    <DL><p>\n" +
//                "    </DL><p>\n" +
//                "    <DT><A HREF=\"http://fanyi.baidu.com/?aldtype=16047#auto/zh\" LAST_MODIFIED=\"1526282232\" ICON=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAMAAABEpIrGAAAABGdBTUEAALGPC/xhBQAAAXFQTFRFt9v8Ho31udz8II72H471hcH6uNv8UKb4AAAAz+f9Z7L5isT63u7+vd78jMX6vN38NJj28fj+0uj9arP5dLn5uNv8Wav47fb+q9X7I4/2a7T5yOP99vr/KZP2+fz/bbX5h8L6yuT9Vqr47vf+Uqf4fb352+3+lcn6bLX55/P+crf58/n/w+H8/f7/ZrL4ns77Qp/3hMD6eLv5msz77vb/TaX3udz86PP+SaP3O5z3LJT2VKj4iMP6J5L2jcX6I5D1RKD39/v//v//brb54/H+v9/8JpH2KpP2pNH7gb/6j8b6Mpf2zOX99Pn/vt78F4n1mMv7Wqv4ptL78Pj+GIr18/n+5fL+H4715PH+XKz49fr/7Pb+HIz1xeL9Q6D3R6L3JZD2O5z22ez9Gov1Ho311ur9JJD2II72jsb6N5r2S6T35vL+HY314O/+kMf6Io/2dbn54fD+FYj1sdf87PX+7/f+QJ73M5j2MJb2////FIj1qLN8hAAAAAl0Uk5TTvRM8vOETb4Aqk9o3wAAAWFJREFUOMuF02Vzg0AQBmBiQN3d3d3dPe7ujSuFpnu/vqSQFBIC7xeOu4fZvRkWI0mc0CLJaAk1SWKkDslER2JqJBscI+QBgWnkgQbjF/5EIibYjz+98asaKACU0E0kEtxeN1t9jBfgXQKEs46tDdukwekDuA98iMAlwzywD1d2hUJoag3guqFELRYYTSb1ADtUC4DM0NkGYDUhAahwsf9tmIwAEPAjIQAuP9V3e5e3ujb0zHrSsRr4YrPJAQsNju52N/fFobCHTBUszk0bZ8b0HenBfvZ8OdcI0ql511/FPoR6hyZyolt88z0gRAGMhIYHxikkAVL5BRvfMhSkQDi1tOoBcCaR6JriEmwPUdQCFCu7waMLgOz+3sEx/ZhrAiW+/N2Jm2FoOtpc4vSschUH+JQokan3UJQGt/n8eR2UJcB/npXAS/k1JAQKv71WeXBwpdFTHF52wAmV9KmKwEnyF2mutxZ+g5+cAAAAAElFTkSuQmCC\" >百度翻译</A>\n" +
//                "    <DT><A HREF=\"https://www.baidu.com/\" LAST_MODIFIED=\"1530668309\" ICON=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAOfSURBVFhHxZZpSFZBFIbfdqykxbAyC0swpE0qBMtAEIqixFaokP6IhZB/+mEFQSEERhLUD0uKiorKgiRFoxCyzZAMQ0oyLGmxsIxCSrJ1Xs4Mzb3fvZ9XLXxguGfmW+bcc95zZgb9VmAAGayfA0avIlD7AmhsA+bEACnT9WI/CexA8W3gxD09UeQsViNVTxQ/fwEld4GbzcCCacD2NCBimP4wDIEcaPsMrC6RTQxDVPKu5AAxY2SeXwZUPxWbJMUCxzbK98IRSAM1z5ybE865TsobnZuThtey3hOBHOj8pg0XZr30oTzdUDM98U+qoOWDNvqApwPMeXO7nigSorXhwqyP9BFbcpw2whDiwJ4KIOMosOkkkHUaaO0AFqmSM2IzcM51kjhJnjbRkcCq2XoSBocDFE3VYz1RNL0TdVPJh9cDU8fJOp+cDx8qc5Zc5AixydgIYH+GfN7eCbxVEXWL2OAow+xzol43hZlA+kyxO74AUaPEtqEga5+LnTJDnnml0rgIf8P/YXnaOCLwRL2xF7bIvDYnjMDSRBm02bTM5oSO512Sp43Dge4f2ggA08OU1bV6h9dEw+ZrN3C9SU80DgfiorThInq0NhQUJcXJsa8SyL2oxKZE606dX+/g720cDmyYrw0LhjMtQWwKaut5eXsbrjO8fim0cbdmx3RtEpA5T08Uk1WpHVonqiYFVaE5NDC8O1XFdH2XuV9vcJdzn09DP1LjgaI18v1T9/WihmV5OdvpRCAHSu7IUWs4vlnCvvuqXnCRORfYtQw4UqOOZ32QMYo8vpcoB23COsAfFlwDKlynWvk2adfUgx/U0470fhzHzDUbk9k8foJcNDhMB+SfUyd2F2Sf4BpPSEaop9L2jMCnLtmcJcNNClZKg7GpfwnsVWXIaDA9TBPhJYROLiyUOa9vB5Um/BqYZwQO3Phbr7x6cXO+EeudozewG+Ze8D8LQhyguOxuZc6Aomo5VDh6C1t5pXXI2YQ40PJeGxqmgF3N7w2C8uiNNlyEOGAEZqh/JSJbPksveJA0BYhVRzTF5/69IXAKEiY6S6f4loSQQnyQL8PAdPHmxJtPmbohU5AUnRe8oHjhWQXuKzYdYgOhcwZqgach33iFio57A1MVhjNbvG9Ong6wB7DJuE+uvsL0MYJe+HZCCu9snZSRnT9ex+LGS2My+WZXpMgalF5aP8oaoSZ4wGUlO9NqE7YV9xVqg43Hb1Ob/+JAcIA/4w5azSe2ic4AAAAASUVORK5CYII=\" >百度一下，你就知道</A>\n" +
//                "    <DT><A HREF=\"http://element-cn.eleme.io/#/zh-CN/component/date-picker\" LAST_MODIFIED=\"1537962591\" ICON=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAUqSURBVFhHtZd7TJZVHMe/5325CcjEwDIYCKXlJaup1NQYkhqthdYfjdY0FTU3tciS5dAkV1OxnOWKmDaQRX/VFvqH4rVs2VBrM4uW5QSk1GUWFy9c3uf0/XGeBx/Y+8L7in02eJ9zeZ7zO7/L+f2Owi2QvMMaHh6GNRYwjx/o0gqf+TqxqWmJ54o9JWhCFmBUhV6qtd4S7lVxc9MBrYHqc0CnT7d4NF47t8iz054aFEELkFqus5TSJXxlyrSRwPoMYPQwM3bmX+Ct48CxC9LSJ+DD6vp8z9fdgwMwoAD3fmIldnmxkVPzk2OBoilAToo92Ie9DcA7J4A/rlIMpcsiw9TaMy+oy/awXwIKMLHSimm2UMgJq6K8KnbZA8BLE4Aorz2BnP7bfGDCHaYtXO8CSn8CdvDvhk+3smtddLQqrXtOdZgZvfErQGq5lcOBd6HU+CdTza6TYuxBcvEasPEksIe2F3LpC29MAu6KNm3hfBvwNrWxv5HagD7F7xXWL/Dst4d76CXA6AorqROoZHf2GNpX7DyV9nbo8HFnPwMfnQaucaduYsKB5dRS/nggwmN3km/+NP5xtlla+qDlxfzGeZ5ubxF6BEiptLI9FqrjIlRswYPA/LGA1yXegfNmR42i1H5IHQq8ScGzk+0O0sV4Lf8F+OAU0NqpW5UPTztO2rME1V57X7zKqHoCSIiyO4lILjuQnYTCDAqwjqZLi7M7yCWaLq8GqG/RX9EcM6SvR1lKYXIO7e0s3kZbiEfn7A59ceFIE7D9R8DHc8LhTvrIU6O6H6d1/ycuaymPx6XylVTQzjqjvlARM+yaBWyd3tuMQnj3iooeY3AJ0Bvx9FCJYIi+TP+pmQNk3m13kgv9fCugAKEi0bI3F3j1ISDSPiuuihkZrksOm7Y/Bi1AwhBg22NA1Wwg3eVwNYz/WdU0I8PW148ZBy1A+ePAHB5EDk08gPIPAcuOUPU8koUU+kQgBi2Acwx3cpelPKBmc9eHGQEOuWk8Px61G364LT5Qe4nhtQco+cHkAkHM8SnN8n4mkEgzBWLQAqz+Fnh+H/AbU7IgyWrVw3RIRoKk7YEYtACf/y7JxpCVZEJw5cSb+UByRtWv5tkft8UEI5kpS7PokDN7O5xEwswvaYr/UwA5vg/MNb8OEgmL+0RCIAIK4M7t/bHhEabiMPPsjoRDrkgILgxZaUqB6VDGXPU6nWmI/fFAOB5+3E8kiB8sZRX1HnOCQztrCi7UbjdvCsDS+qTUdPUtpi3n+nI600Gq185gfrlyw0RCnisSBEnH++iQa1gpxdqp56/r7OMazE/HTI+7HqiwJD9XR3rV0MXjgBVc3L377y4CxbWmAnYTHwn807Mfk/+lDhABHNwFSVuHbuNmcxsWeOghLgGEMWU6oSNCr2dxsEI8u2hy79130USV/NA2qWz6lJiySxF6EYU3KddwlLXEhmBKMjejKqxM+sMWpVSGZLli1x1AuEy1b/4e+OKsaT/LXFBIVY9wnXgNLN2koJFSLuiitC9p5VY+tbdVbkEv3g8UMNU69hTqeBFjJYWx8XYHkYNHilbJgu1SlmusjY5RH4dUlruRe2BYGIr4+EpitPJK+f3MPf5f3M0yXcp1U8zoDyPCVfEtX0z6kr5LT7csvZlbnjpphIn/ccPNmGhCClcJRYZYLbUi6j5qRvsnaAEcWD3n8a1NYUqlyoVEzg65oNBBm6nugoaFqsKeGhQhCyDwojpMQRfy7YXiYeyq0FAlXLxPkA4E8B88vrrvpZZq2AAAAABJRU5ErkJggg==\" >组件 | Element</A>\n" +
//                "    <DT><A HREF=\"https://cn.vuejs.org/v2/guide/\" LAST_MODIFIED=\"1537962599\" ICON=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAOLSURBVFhH7ZdLSFRRGMe/c+88TEt7GEYUPYiIIoLSglJLArNaFO2CHrRq24sKq4Exqk1QuxYtgixa1SKInmASRVEGEUGLyiIQAmmhpBM69/T9z/24Z+beGWfGRZv6wcHv/53vNec+ZqR/HtX24Gw3/13jS8vYxyH6/eCHKIt26cabe9fPi8yjcfu+TpWlPSIDkh0NFF9WKyqPPrXp4bkNyss+I6WUOH08TSO3vpM3+FscPpp0hqbEVry5c+2LuAyNuw4sptHxD4pUlbgMTn2SqnfPZyO/PGnGcVucp1tOPdekbonbwgnJjbNFWEyDjHdRpIV94ebA1Ag3Z9ATvR2IrKKTPM8vs5ODO28KxZZMFWVRWu9s2rF/s0iCDZ/IAOSiRhj0Qk/YwWht97vO8GXoEhmgh8ZopPsb6XEtHkHR++qmRathjrzuf8sfaaXxCyqmqHrvAlK1cfHkoHWqZ2vqLExzAmC0ruYib/SLDECB+OoZonLghqN9Xw9ihZsD5BRp3m96CcEAL9cfHfUcOi4yj0QjF5saE2XxspTGEhmAWOQUAj3QS6QdAPS2p25rTT0iLXGHks31IiyK9CwskQEmlnPCoDZ6iDTkR3E1pfRhPtJx8QTElk4jd270hgqDGMRG4JqmNvcQjyEyZk9H6h0/61dFWvh2TbbyIxV9oiwTxKAmaosMiJ4To6uyKT6unyIDnIYkxZfXiYoSX15rYsKgFmqKzKPgAL1t6UG+XSI3F0isn0UqGU1TCYf3oveJj5f2a0YpOAAYnj3nCl+sDyIDVLVLibUzRVkS62aavTCogVoiIxQdoK/x4Jjy6AiboTcQH/Wq6eTMSIjiImzDVwCNGqglOkLRAUDPtjOPuMZdkRaXvyda7HEbm31R9F2/RnEmHAB4FDvGZ5ARGeAuqqHYQn/BjsA5JrcEJQfo7ej8pBx1WWQeidZ6swqBHOSKLErJAUCmJo4fIAO+suDa594LOQxITknKGuBF84lhrahTZEkQixyRE1LWAGBj+3g3X9dXIovDMSa2TMoeIK3SnueqQ2x6vqcgJgaxoktS9gCgt/30S36n3xQZAXuIEVkWFQ0AYk6Sf0rpAtdXD/t7lVHxAE/aTwzwR70g0sI+s1chFQ8AvKrsJT7uzyJx9J/hE1kRkxqAv9ky/AURvOVgwyfyr8H/VXU9xoLtuypnUicgaE+5h7Fg+67/VArRHxy8LxP0xSGHAAAAAElFTkSuQmCC\" >Vue.js</A>\n";
        Document doc = Jsoup.parse(html);
        Elements aTag = doc.getElementsByTag("a");
        if (aTag.size() <= 200) {
            for (Element a : aTag){
                String url = a.attr("href");
                String urlName = a.text();
                String icon = a.attr("icon");
                ShareContent shareContent = new ShareContent();
                shareContent.setIcon(icon);
                shareContent.setUrlName(urlName);
                shareContent.setUrl(url);
                contentService.addShareContent(shareContent, getUid(req));
            }
        } else {
            throw new BaseException("1005","Cannot import more than 200 records at a time");
//            return ResultUtil.error(1005,"Cannot import more than 200 records at a time");
        }
        return ResultUtil.success("success");
    }

}
