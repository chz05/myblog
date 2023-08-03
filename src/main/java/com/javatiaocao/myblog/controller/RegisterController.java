package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.service.UserService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import com.javatiaocao.myblog.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName RegisterController.java
 * @Description TODO
 * @createTime 2023/08/01
 */

@RestController
@Slf4j
public class RegisterController {

    @Resource
    private UserService userService;
    @PostMapping("/register")
    @Transactional
    public String register(User user, HttpServletResponse response){
        //check the username whether exist or not
        try{
            if (userService.UserNameIsExist(user.getUsername())){
                return JsonResult.fail(CodeType.USERNAME_EXIST).toJSON();
            }
            // Encode the password
            String password = new MD5Util().encode(user.getPassword());
            System.out.println("After encode, the password is: " + password);
            user.setPassword(password);
            //remove all spaces in username.
            user.setUsername(user.getUsername().replaceAll("\\s+", ""));
            //register
            DataMap data = userService.InsertUserCheck(user);
            return JsonResult.build(data).toJSON();
        }catch(Exception e){
            log.error("registerController register exception", user, e);
        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}
