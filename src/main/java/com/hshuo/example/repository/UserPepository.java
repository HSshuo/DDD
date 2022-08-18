package com.hshuo.example.repository;

import com.hshuo.example.DP.PhoneNumberDP;
import com.hshuo.example.DP.UserIdDP;
import com.hshuo.example.entity.User;

/**
 * @author SHshuo
 * @data 2022/8/18--16:40
 *
 * repository：{
 *     1. 对应 Entity 的操作
 *     2. 进一步封装 mapper 层，承担一个 AVL 防腐层的作用
 * }
 *
 */
public interface UserPepository {
    User find(UserIdDP id);
    User find(PhoneNumberDP phone);
    User save(User user);
}
