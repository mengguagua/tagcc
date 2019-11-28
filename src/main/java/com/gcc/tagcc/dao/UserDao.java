package com.gcc.tagcc.dao;

import com.gcc.tagcc.entity.User;
import com.gcc.tagcc.untils.annotation.MyBatisDao;


/**
 * @author gaoccc
 * @create 2019-04-30 23:24
 */
@MyBatisDao
public interface UserDao {

    int register(User user);

}
