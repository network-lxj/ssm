package com.example.ssm_demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 组织机构
 */
@Data
@TableName("org")
public class Org {
    /**
     * id
     */
    private Integer id;
    /**
     * 父级id
     */
    private Integer pid;
    /**
     * 名称
     */
    private String name;
    /**
     * 级别
     */
    private String rank;
    /**
     * 区域编码
     */
    private String areaCode;

    public Org() {
    }

    public Org(Integer id, Integer pid, String name, String rank, String areaCode) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.rank = rank;
        this.areaCode = areaCode;
    }

    @Override
    public String toString() {
        return "Org{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", rank='" + rank + '\'' +
                ", areaCode='" + areaCode + '\'' +
                '}';
    }
}
