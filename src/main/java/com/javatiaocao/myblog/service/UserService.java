package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.utils.DataMap;

/**
 * @ClassName UserService.java
 * @Description TODO
 * @createTime 2023/07/31
 */
public interface UserService {

    //use phone number to select
    User selectByPhoneNumber(String phone);

    Boolean UserNameIsExist(String userName);

    DataMap InsertUserCheck(User user);
}
