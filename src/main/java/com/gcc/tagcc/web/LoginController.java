package com.gcc.tagcc.web;


import com.gcc.tagcc.entity.User;
import com.gcc.tagcc.service.UserService;
import com.gcc.tagcc.untils.UuidUntil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author gaoccc
 * @create 2019-04-30 22:58
 */
@RestController
@RequestMapping("/base/")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/regist")
    public Object regist(){
        User user = new User();
        user.setUsername("15957108449");
        user.setNickname("gaocc");
        userService.register(user);
        return "登录成功";
    }

    @RequestMapping("/login")
    public Object login(@RequestBody User user) {
        Object result = userService.login(user);
        return result;
    }

}
