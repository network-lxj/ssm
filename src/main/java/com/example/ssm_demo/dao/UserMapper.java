package com.example.ssm_demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ssm_demo.entity.Org;
import com.example.ssm_demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {


}
