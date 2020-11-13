package com.gcc.tagcc.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.gcc.tagcc.entity.Email;
import com.gcc.tagcc.entity.EmailConfig;
import com.gcc.tagcc.untils.HttpUtil;
import com.gcc.tagcc.untils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    RedisUtil redisUtil;

    // 未登录返回游客id
    private static final String tourist = "99999999";

    public String getUid(HttpServletRequest request){
        String token = request.getHeader("token");
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

    public void sendSimpleEmail(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email.getFrom());
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getText());
        emailConfig.javaMailSender().send(message);
    }

    public Boolean checkCode(HttpServletRequest request, String sign, String code) {
        String ip = HttpUtil.getIpByRequest(request);
        String key = sign.concat(ip);
        String redisCode = redisUtil.get(key, redisUtil.db1);
        if (code.equals(redisCode)) {
            return true;
        }
        return false;
    }

    public void pushCode(HttpServletRequest request, String sign, String code) {
        String ip = HttpUtil.getIpByRequest(request);
        String key = sign.concat(ip);
        if (redisUtil.get(key, redisUtil.db1) == null || "".equals(redisUtil.get(key, redisUtil.db1))) {
            redisUtil.set(key, String.valueOf(code), redisUtil.db1);
            // 300s失效
            redisUtil.expire(key, 300, redisUtil.db1);
        }
    }

}
