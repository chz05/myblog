package com.javatiaocao.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName BackControl.java
 * @Description TODO
 * @createTime 2023/08/01
 */
@Controller
public class BackControl {

    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        //判断是否有需要回跳的url，有则将需要回跳的url保存在响应头中
        response.setHeader("lastUrl", (String) request.getSession().getAttribute("lastUrl"));
        return "index";
    }


    /**
     * 跳转注册页
     */
    @GetMapping("/register")
    public String register(){
        return "register";
    }
}
