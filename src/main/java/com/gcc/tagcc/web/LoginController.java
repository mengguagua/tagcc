package com.gcc.tagcc.web;


import com.gcc.tagcc.annotation.RequestLimit;
import com.gcc.tagcc.entity.User;
import com.gcc.tagcc.service.UserService;
import com.gcc.tagcc.untils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author gaoccc
 * @create 2019-04-30 22:58
 */
@RestController
@RequestMapping("/base/")
public class LoginController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestLimit(count = 10, time = 1000 * 60 * 60 * 24)
    @RequestMapping("register")
    public Object register(HttpServletRequest req, @RequestBody User user){
        Boolean ret = checkCode(req, user.getUsername(), user.getCode());
        if (ret) {
            userService.register(user);
        } else {
            return ResultUtil.error(1010,"验证码错误");
        }
        return ResultUtil.success();
    }

    @RequestMapping("login")
    public Object login(@RequestBody User user) {
        Object result = userService.login(user);
        return result;
    }

}
