package com.example.ssm_demo;

import com.example.ssm_demo.dao.OrgMapper;
import com.example.ssm_demo.dao.UserMapper;
import com.example.ssm_demo.entity.Org;
import com.example.ssm_demo.entity.User;
import com.example.ssm_demo.service.OrgService;
import com.example.ssm_demo.util.GrabUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
class SsmDemoApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrgService orgService;

    @Test
    public void testSelect() {
//        userMapper.deleteById(1);
        User user=new User(0,"张三",18,"中国");
        userMapper.insert(user);
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void batchOrg() throws Exception{


        Set<Org> list=GrabUtil.parseOrg();
        System.out.println(orgService.saveBatch(list));
    }


}
