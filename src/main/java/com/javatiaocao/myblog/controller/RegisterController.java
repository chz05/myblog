package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RegisterController.java
 * @Description TODO
 * @createTime 2023/08/01
 */

@RestController
public class RegisterController {

    @PostMapping("/register")
    public String register(User user){

        return "register already";
    }
}
