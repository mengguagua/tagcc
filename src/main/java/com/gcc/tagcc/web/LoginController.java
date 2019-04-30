package com.gcc.tagcc.web;


import com.gcc.tagcc.entity.User;
import com.gcc.tagcc.service.UserService;
import com.gcc.tagcc.untils.UuidUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author gaoccc
 * @create 2019-04-30 22:58
 */
@Controller
public class LoginController {

    @Autowired
    private User user;

    @Autowired
    private UserService userService;

    @RequestMapping("/regist")
    @ResponseBody
    public Object regist(){
        user.setId(UuidUntil.getUUID());
        user.setName("gaocc");
        user.setLevel(1);
        user.setPassword("123456");
        userService.regist(user);
        return "登录成功";
    }

}
