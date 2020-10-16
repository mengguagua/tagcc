package com.gcc.tagcc.web;


import com.gcc.tagcc.entity.User;
import com.gcc.tagcc.service.UserService;
import com.gcc.tagcc.untils.UuidUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author gaoccc
 * @create 2019-04-30 22:58
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/regist")
    @ResponseBody
    public Object regist(){
        User user = new User();
        user.setUsername("15957108449");
        user.setNickname("gaocc");
        userService.register(user);
        return "登录成功";
    }

    @RequestMapping("/login")
    public Object login(@RequestBody User user){
        userService.login(user);
        return "success";
    }

}
