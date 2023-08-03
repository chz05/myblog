package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Role;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.utils.DataMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UserMapper.java
 * @Description TODO
 * @createTime 2023/07/31
 */
@Mapper
@Repository
public interface UserMapper {
    User selectUserByPhoneNumber(String phoneNumber);
    User selectUserByUserName(String userName);
    void insertUser(User user);
    void insertUserRole(@Param("userID") int userID, @Param("RoleID") int RoleID);
    void updateRecentlyLanded(@Param("phone") String phone, @Param("RecentlyLandedTime") String RecentlyLandedTime);

    List<Role> selectRoleByUserPhone(String phone);
}
