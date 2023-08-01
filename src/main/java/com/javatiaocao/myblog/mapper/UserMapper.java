package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserMapper.java
 * @Description TODO
 * @createTime 2023/07/31
 */
@Mapper
@Repository
public interface UserMapper {
    User selectUserByPhoneNumber(String phoneNumber);
}
