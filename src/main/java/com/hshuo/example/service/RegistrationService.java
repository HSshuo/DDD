package com.hshuo.example.service;

import com.hshuo.example.DP.PhoneNumberDP;
import com.hshuo.example.entity.User;

/**
 * @author SHshuo
 * @data 2022/8/18--16:32
 *
 * Domain Service: 封装多个 Entity 改变的服务
 */
public interface RegistrationService {

    public User registry(String name, PhoneNumberDP phone);
}
