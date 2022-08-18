package com.hshuo.example.mapper;

import com.hshuo.example.entity.User;

/**
 * @author SHshuo
 * @data 2022/8/18--17:02
 *
 * 连接数据库、写 SQL 语句
 */
public interface UserMapper {

    User selectById(String id);

    User selectByPhone(String phone);

    void insert(User user);
}
