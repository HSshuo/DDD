package com.hshuo.example.entity;

import com.hshuo.example.DP.RealnameInfoDP;
import com.hshuo.example.DP.LabelDP;
import com.hshuo.example.DP.PhoneNumberDP;
import com.hshuo.example.DP.SalesRepIdDP;
import com.hshuo.example.DP.UserIdDP;
import com.hshuo.example.repository.UserPepository;

/**
 * @author SHshuo
 * @data 2022/8/18--15:38
 *
 * Entity: {
 *     1. 有状态、属于业务对象、对应数据库落地实现
 *     2. 可能包含多个 DP
 * }
 *
 */
public class User {
    /**
     * 用户id、DP
     */
    private UserIdDP userId;

    /**
     * 用户手机号、DP
     */
    private PhoneNumberDP phone;

    /**
     * 用户标签、DP
     */
    private LabelDP label;

    /**
     * 绑定销售组ID、DP
     */
    private SalesRepIdDP salesRepId;


    private Boolean fresh = false;
    private UserPepository userPepository;


    /**
     * 构造函数
     * @param info
     * @param name
     * @param phone
     */
    public User(RealnameInfoDP info, String name, PhoneNumberDP phone){
        info.check(name);
        initId(info);
        labelledAS(info);

        User user = userPepository.find(phone);
        this.salesRepId = user.salesRepId;
    }

    /**
     * 对 this.userId 赋值
     * @param info
     */
    public void initId(RealnameInfoDP info) {
        // 处理。。。。
    }

    /**
     * 对 this.label 赋值
     * @param info
     */
    public void labelledAS(RealnameInfoDP info) {
        // 处理。。。。
    }

    public void fresh() {
        this.fresh = true;
    }

}
