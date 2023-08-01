package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName Hello.java
 * @Description TODO
 * @createTime 2023/07/31
 */
@RestController
public class Hello {

    @Resource
    private UserService userService;

    @GetMapping("/hello")
    public String hello(){
        User user = userService.selectByPhoneNumber("18888888888");

        return user.toString();
    }
}
