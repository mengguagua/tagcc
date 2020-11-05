package com.gcc.tagcc.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.gcc.tagcc.untils.ResultUtil;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    // 未登录返回游客id
    private static final String tourist = "99999999";

    public String getUid(HttpServletRequest req){
        String token = req.getHeader("token");
        // 获取 token 中的 userId
        try {
            if("null".equals(token)) {
                return tourist;
            }
            return JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j){
            throw new RuntimeException("401");
        }
    }

}
