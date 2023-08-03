package com.javatiaocao.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;

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


    /**
     * 跳转登录页
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 登录前尝试保存上一个页面的url
     */
//    @GetMapping("/toLogin")
//    @ResponseBody
//    public void toLogin(HttpServletRequest request){
//        //保存跳转页面的url
//        String lastUrl = request.getHeader("Referer");
//        if(lastUrl != null){
//            try {
//                URL url = new URL(lastUrl);
//                if(!SLASH_SYMBOL.equals(url.getPath())){
//                    request.getSession().setAttribute("lastUrl", lastUrl);
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
