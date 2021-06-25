package com.example.ssm_demo.service;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ssm_demo.dao.UserMapper;
import com.example.ssm_demo.entity.User;
import com.example.ssm_demo.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public interface UserService extends IService<User> {

    User findUserByUserName(String name);

    Resp login(String username, String password);
}
