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

    @Autowired
    ContentDao contentDao;

    public Object addShareContent(String weight, String collection, String icon, String urlName, String url){
        BigDecimal wt = BigDecimalUtil.createBigDecimal(weight);
        BigDecimal cn = BigDecimalUtil.createBigDecimal(collection);
        return contentDao.addShareContent(wt, cn, icon, urlName, url);
    }

    public ArrayList<ShareContent> queryShareContent(){
        return contentDao.queryShareContent();
    }


    public void deleteShareContent(int id) {
        contentDao.deleteShareContent(id);
    }
}
