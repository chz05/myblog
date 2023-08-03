package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.constant.RoleType;
import com.javatiaocao.myblog.mapper.UserMapper;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.utils.DataMap;
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

    @Override
    public Boolean UserNameIsExist(String userName) {
        if (userMapper.selectUserByUserName(userName) == null){
            return false;
        }
        return true;
    }

    @Override
    public DataMap InsertUserCheck(User user) {
        //the username length
        //the phone number length
        if (!checkUsernameLength(user)){
            return DataMap.fail(CodeType.USERNAME_TOO_LANG);
        }
        if (!checkPhoneNumber(user)){
            return DataMap.fail(CodeType.PHONE_ERROR);
        }

        user.setAvatarImgUrl("www.javatiaocao.com");

        //insert into user_role table with default Role_id = 1 (ROLE_USER)
        userMapper.insertUser(user);

        userMapper.insertUserRole(user.getId(), RoleType.ROLE_USER.getRoleID());
        return DataMap.success();
    }

    private boolean checkUsernameLength(User user){
        if (user.getUsername().length() > 35 || user.getUsername().length() <= 0){
            return false;
        }
        return true;
    }

    private boolean checkPhoneNumber(User user){
        if (user.getPhone().length() != 11){
            return false;
        }

        if (!user.getPhone().matches("\\d+")){
            return false;
        }

        //check the phone number whether exist or not.
        if (userMapper.selectUserByPhoneNumber(user.getPhone()) != null){
            return false;
        }
        return true;
    }

}
