package com.example.ssm_demo.service;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.example.ssm_demo.dao.UserMapper;
import com.example.ssm_demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void test(){
        userMapper.selectList(new QueryChainWrapper<>(userMapper).select());
    }

}
