package com.example.ssm_demo.controller;

import com.example.ssm_demo.util.MD5Utils;
import com.example.ssm_demo.util.RSAUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @RequestMapping("getPublickey")
//    @ResponseBody
    public String getUserTest(){
        String key =RSAUtils.generateBase64PublicKey();
        return key;
    }

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
