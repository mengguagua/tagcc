package com.gcc.tagcc.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gcc.tagcc.dao.UserDao;
import com.gcc.tagcc.entity.User;
import com.gcc.tagcc.untils.Md5Util;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author gaoccc
 * @create 2019-04-30 23:20
 */
@Service
public class UserService {

    @Autowired
    public UserDao userDao;

    public int register(User user){
        return userDao.register(user);
    }

    public Object login(User user) {
        HashMap<String, Object> map = new HashMap<>();
        String username = user.getUsername();
        String password = user.getPassword();
        User dbUser = userDao.getUserByUsername(username);
        if (dbUser == null) {
            map.put("msg","用户名不存在");
        } else {
            if (!Md5Util.md5Password(password).equals(dbUser.getPassword())) {
                map.put("msg","密码错误");
            } else {
                String token = getToken(dbUser);
                map.put("token",token);
                map.put("user",dbUser);
            }
        }
        return map;
    }

    public User findUserById(String userId) {
        return userDao.findUserById(userId);
    }

    public String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

}
