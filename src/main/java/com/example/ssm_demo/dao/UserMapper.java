package com.example.ssm_demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ssm_demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
