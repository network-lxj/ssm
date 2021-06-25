package com.example.ssm_demo.controller;

import com.example.ssm_demo.service.UserService;
import com.example.ssm_demo.util.MD5Utils;
import com.example.ssm_demo.util.RSAUtils;
import com.example.ssm_demo.util.VerifyCodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("ssm/user")
public class UserController {



    @Resource
    UserService userService;

    @RequestMapping("add")
    public String userAdd(String username,String password){
        try{
            //rsa解密
            username = RSAUtils.decryptBase64(username);
            password = RSAUtils.decryptBase64(password);

            //加盐md5加密
            password=MD5Utils.generate(password);


        }catch (Exception e){
            e.printStackTrace();
            return "添加失败";
        }
        return "添加成功";
    }

}
