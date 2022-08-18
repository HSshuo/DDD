package com.hshuo.example.repository.Impl;

import com.hshuo.example.DP.PhoneNumberDP;
import com.hshuo.example.DP.UserIdDP;
import com.hshuo.example.entity.User;
import com.hshuo.example.mapper.UserMapper;
import com.hshuo.example.repository.UserPepository;

/**
 * @author SHshuo
 * @data 2022/8/18--17:01
 */
public class UserPepositoryImpl implements UserPepository {

    private UserMapper userMapper;

    @Override
    public User find(UserIdDP id) {
        return userMapper.selectById(id.value());
    }

    @Override
    public User find(PhoneNumberDP phone) {
        return userMapper.selectByPhone(phone.getNumber());
    }

    @Override
    public User save(User user) {
        userMapper.insert(user);
        return user;
    }
}
