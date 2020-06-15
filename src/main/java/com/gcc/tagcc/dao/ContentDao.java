package com.gcc.tagcc.dao;

import com.gcc.tagcc.entity.ShareContent;
import com.gcc.tagcc.untils.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author gaocc
 * @create 2019-11-27 11:52
 */
@MyBatisDao
public interface ContentDao {

    int addShareContent(@Param("weight") BigDecimal weight, @Param("collection") BigDecimal collection,
                        @Param("icon") String icon, @Param("urlName") String urlName, @Param("url") String url);

    ArrayList<ShareContent> queryShareContent();

    void deleteShareContent(@Param("id") int id);
}
