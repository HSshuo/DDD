package com.hshuo.example.DP;

import javax.xml.bind.ValidationException;

/**
 * @author SHshuo
 * @data 2022/8/18--13:16
 *
 * 无状态、组成实体的基础
 * 遵循三条原则：{
 *     1. 让隐性的概念显性化
 *     2. 让隐性的上下文显性化
 *     3. 封装多对象行为
 * }
 *
 */
public class PhoneNumberDP {

    /**
     * 正常的 Pojo 层的部分
     * 定义属性、getter、setter方法
     */
    private final String number;
    private final String pattern = "^0?[1-9]{2,3}-?\\d{8}$";

    public String getNumber() {
        return number;
    }



    /**
     * 原本在 controller 层判断的部分，放置在 PO 层
     * 将校验逻辑与业务逻辑分离开，实现充血模型
     * @param number
     * @throws ValidationException
     */
    public PhoneNumberDP(String number) throws ValidationException {
        if(number == null) {
            throw new ValidationException("number 不能为空");
        }else if(isValid(number)) {
            throw new ValidationException("number 格式错误");
        }

        this.number = number;
    }

    private boolean isValid(String number) {
        return number.matches(pattern);
    }



    /**
     * 将与之相关的行为也放在一起，使得内聚
     * 将传参改为 PhoneNumberDP 类，方便扩展、同时接口语义明确不易发生乱序
     * @return
     */
    public String getAreaCode() {
        return number;
    }

    public String getOperatorCode(PhoneNumberDP phoneNumberPO) {
        return number;
    }

}
