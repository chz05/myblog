package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.UserMapper;
import com.javatiaocao.myblog.model.Role;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.utils.TimeUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName UserDetialsImp.java
 * @Description TODO
 * @createTime 2023/08/02
 */
public class UserDetailsServiceImp implements UserDetailsService {

    @Resource
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        //check the user whether exist or not
        User user = userMapper.selectUserByPhoneNumber(phone);
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException(CodeType.USERNAME_NOT_EXIST.getMessage());
        }

        //update the current time to the current user.
        userMapper.updateRecentlyLanded(phone, new TimeUtil().getFormatDateForSix());

        //set the RoleList in user
        List<Role> roles = userMapper.selectRoleByUserPhone(phone);
        user.setRoleList(roles);

        //get the role set using
        ArrayList<SimpleGrantedAuthority> auths = new ArrayList<SimpleGrantedAuthority>();
        for (Role role : user.getRoleList()) {
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(), auths);
    }
}
