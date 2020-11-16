package com.gcc.tagcc.service;

import com.gcc.tagcc.dao.ContentDao;
import com.gcc.tagcc.entity.ShareContent;
import com.gcc.tagcc.untils.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author gaocc
 * @create 2019-11-27 11:39
 */
@Service
public class ContentService {

    // 游客id
    private static final String tourist = "99999999";

    @Autowired
    ContentDao contentDao;

    public Object addShareContent(ShareContent shareContent, String userId){
        BigDecimal wt = BigDecimalUtil.createBigDecimal(shareContent.getWeight());
        BigDecimal cn = BigDecimalUtil.createBigDecimal(shareContent.getCollection());
        return contentDao.addShareContent(wt, cn, shareContent.getIcon(), shareContent.getUrlName(), shareContent.getUrl(), userId);
    }

    public ArrayList<ShareContent> querySelfContent(String userId){
        return contentDao.querySelfContent(userId);
    }


    public void deleteShareContent(int id) {
        contentDao.deleteShareContent(id);
    }

    public void upShareContent(String weight, int id) {
        contentDao.upShareContent(weight, id);
    }

    public void addTouristShareContent(ShareContent shareContent) {
        contentDao.addTouristShareContent(shareContent.getUrl(),shareContent.getUrlName(),tourist);
    }
}
