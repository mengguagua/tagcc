package com.gcc.tagcc.service;

import com.gcc.tagcc.dao.UserDao;
import com.gcc.tagcc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gaoccc
 * @create 2019-04-30 23:20
 */
@Service
public class UserService {

    @Autowired
    public UserDao userDao;

    public int regist(User user){
        return userDao.regist(user);
    }

}
