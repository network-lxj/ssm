package com.example.ssm_demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    private Integer id;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 姓名
     */
    private String name;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 地址
     */
    private String address;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 组织机构id
     */
    private Integer orgId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;


    public User() {
    }

    public User(Integer id, Integer sex, String name, String birthday, Integer age, String address, String idCard, Integer orgId, String username, String password) {
        this.id = id;
        this.sex = sex;
        this.name = name;
        this.birthday = birthday;
        this.age = age;
        this.address = address;
        this.idCard = idCard;
        this.orgId = orgId;
        this.username = username;
        this.password = password;
    }
}
