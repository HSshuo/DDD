# DDD(领域驱动设计)

## 介绍
DDD 全程 Domain Driven Design，是基于面向对象的工程设计方法论

##### 大致流程
1. 首先对需要处理的业务问题进行总览
2. 然后领域对象（Entity）进行划分，明确每个领域对象的包含的信息和职责边界。并进行跨对象、多对象的逻辑组织（Domain Service）
3. 接着在上层应用中根据业务描述去编排 Entity 和 Domain Service
4. 最后再做一些下水道工作，去对下层数据访问、RPC调用去做一些具体的实现

<br>
<br>

## 关注点
1. 接口语义与参数校验
2. 核心业务逻辑清晰度
3. 单元测试可行性

##### 接口语义与参数校验
- 方便拓展
- 接口语义明确，不易发生传参乱序
- 将参数校验放置在 DP 层，使得通过的数据都是合法的，同时将参数校验异常与业务逻辑异常区分开
- 参数校验逻辑复用、内聚
``` java
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

```

##### 核心业务逻辑清晰度
- 核心业务可能需要其他数据，然后需要什么数据直接获取，就好像胶水粘的一样、耦合度高，所以我们应该避免胶水逻辑

解决：
- 在设计的时候就将修改入参为封装类
- 无法避免，可以考虑将入参的获取方法内聚在对应的 PO 里面，调用的时候直接在需要的方法参数里面获取即可，不用写在外面

胶水逻辑：
``` java
String code = phone.getAreaCode();
String id = phone.getId();
salesRepRepo.findRep(code, id);
```
改写：
``` java
salesRepRepo.findRep(phone.getAreaCode(),  phone.getId());

// 或者 事先设计好
salesRepRepo.findRep(phone);
```

<br>
<br>

## Pojo 与 DP
- 传统 MVC 模型里面的 Pojo 只包含属性和 getter()、setter() 方法，属于贫血模型
- DDD 里面的 DP（Domain Primitive）包含 初始化、检验、属性处理以及其相关的职责等多种逻辑，属于充血模型

##### DP 的概念/原则
- 在DDD里，DP可以说是一切模型、方法、架构的基础，它是在特定领域、拥有精确定义、可以自我验证，拥有行为的对象，可以认为是领域的最小组成部分

**原则**：
1. 让隐性的概念显性化

将 DP 属性相关的其他职责增加 getter() 使其显性化。例如：手机号属性、相关的电话地址等隐性概念通过getAreaCode() 使其显性化

<br>

2. 让隐性的上下文显性化

将 DP 属性的上下文属性，暴露出来，定义为成员变量。例如：手机号属性、相关的上下文属性区号协议，将其定义为 private String protocol

<br>

3. 封装多对象行为

一个 DP 封装多个 DP 的行为

<br>

##### 充血模型 Rich Domain Model
- 定义赋予对象除了属性、属性读写方法外，包含业务逻辑的领域行为

**好处**：
- 模型本身高度内聚、表达力很强，将业务操作公共的部分抽离，有效避免高度耦合的代码

**问题**：

需要结合实际的问题分析
- 哪些逻辑应该被当前对象包含
- 哪些逻辑应该被拆分到其他对象中

<br>
<br>

## Entity

##### Entity 与 DP 的区别
- Entity：属于业务对象，有状态、领域实体，最终要在数据库落地实现的，应对数据表的变化
- DP：无状态，组成实体的基础类型

<br>

##### 判断有无状态
1. 该对象是否存在生命周期
2. 程序是否需要追踪该对象的变化事件

例子：体育馆预定座位
- DP：无状态，入座即坐
- Entity：有状态，每一个座位有唯一的编号，需要被追踪 Entity

<br>
<br>

## Domain Service / Repository
##### Repository
- 抽象并封装外部数据访问逻辑
- 获取对应的 Entity 进行操作，应对操作的变化，动作例如：select()、find()、save()
- 进一步封装 Mapper 层

<br>

##### Domain Service
- 涉及多个 Entity 状态改变的服务
- 用于封装多个 Entity 或者 跨业务域的逻辑

<br>

##### 防腐层 AVL 
**问题**：
- 由于可能外部依赖耦合严重，不属于当前域内的服务和设施，比如：数据库、中间件、RPC接口

**解决**：
- 为了避免外部依赖变化所导致的内部系统的改造程度，可以狭义的理解为系统的可维护性
- 引入防腐层，将技术与业务隔离，作为抽象接口，具体实现类实现接口，重写方法将变动范围控制在具体实现类和配置文件内部，保证了核心业务逻辑的稳定

<br>
<br>

## 宏观概念
##### 统一语言 UL（Ubiquitous Language）
- 防止引发歧义，需要统一共识
- 对业务领域知识不断消化

所以提倡业务领域专家和技术人员一起去建模，通过形成 UL，消化业务知识，进而建设良好的模型

<br>
<br>

## 参考
- [博客总结地址](https://blog.nowcoder.net/n/29ef8eaa1efe4359b80c52fa1d6e9b94)
- 持续更新中。。。。

<br>
<br>
