package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.mapper.UserMapper;
import com.javatiaocao.myblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName UserServiceImp.java
 * @Description TODO
 * @createTime 2023/07/31
 */
@Service
public class UserServiceImp implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public User selectByPhoneNumber(String phoneNumber) {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }
}
