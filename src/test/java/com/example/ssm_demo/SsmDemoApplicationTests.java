package com.example.ssm_demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.ssm_demo.dao.*;
import com.example.ssm_demo.entity.*;
import com.example.ssm_demo.enums.AreaRnkEnum;
import com.example.ssm_demo.service.*;
import com.example.ssm_demo.shiro.realm.CustomerRealm;
import com.example.ssm_demo.util.GrabUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
class SsmDemoApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrgService orgService;

    @Resource
    private OrgMapper orgMapper;

    @Resource
    private OrgOwnMapper orgOwnMapper;

    @Resource
    private OrgOwnService orgOwnService;

//    @Resource
//    ShardedJedisPool shardedJedisPool;

    @Resource
    private FamilyNameService familyNameService;

    @Resource
    private ChineseCharService chineseCharService;

    @Resource
    private UserService userService;

    @Test
    public void testSelect() {
//        userMapper.deleteById(1);
//        User user=new User(0,"张三",18,"中国");
//        userMapper.insert(user);
//        System.out.println(("----- selectAll method test ------"));
//        List<User> userList = userMapper.selectList(null);
//        userList.forEach(System.out::println);
    }

    @Test
    public void batchOrg() throws Exception{
        Set<Org> list=GrabUtil.parseOrg();
        System.out.println(orgService.saveBatch(list));
    }

    @Test
    public void filterOrgName(){
        QueryWrapper<Org> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("rank","5");
        queryWrapper.like("name","村委会");
        queryWrapper.or().like("name","居委会");
        List<Org> list=orgMapper.selectList(queryWrapper);
        if(!list.isEmpty()){
            System.out.println(list.size());
            list.forEach(org -> {
                System.out.println(org);
                if(org.getName().contains("村委会")){
                    if(org.getName().split("村委会").length > 0){
                        org.setName(org.getName().split("村委会")[0]);
                    }

                }
                if(org.getName().contains("居委会")){
                    if(org.getName().split("居委会").length > 0){
                        org.setName(org.getName().split("居委会")[0]);
                    }
                }
            });
            System.out.println(orgService.updateBatchById(list));
        }
    }

    @Test
    public void initOrgOwn() throws Exception{
        List<OrgOwn> orgOwns=new ArrayList<>();
        List<Org> orgs=orgMapper.selectList(null);
        Iterator iterList=orgs.iterator();
        while (iterList.hasNext()){
            Org org=(Org)iterList.next();
            orgOwns.add(new OrgOwn(0,org.getId(),org.getId()));
            orgOwns=getOrgOwnsByPid(org.getId(),org.getId(),orgOwns);
        }

        orgOwnService.saveBatch(orgOwns);

    }

    public List<OrgOwn> getOrgOwnsByPid(Integer orgId,Integer pid,List<OrgOwn> orgOwns){
        QueryWrapper<Org> queryWrapper=new QueryWrapper<Org>();
        queryWrapper.eq("pid",pid);
        List<Org> orgList=orgMapper.selectList(queryWrapper);
        if(orgList.isEmpty()){
            return orgOwns;
        }
        orgList.forEach(org -> {
            orgOwns.add(new OrgOwn(0,orgId,org.getId()));
            if(!org.getRank().equals(AreaRnkEnum.village.getValue())){
                getOrgOwnsByPid(orgId,org.getId(),orgOwns);
            }
        });
        return orgOwns;
    }

    public void test(){
        Map<Integer,Set<Integer>> map=new HashMap<>();
        List<Org> orgs = orgMapper.selectList(null);
        orgs.forEach(org -> {
            Set<Integer> ids=new HashSet<>();
            List<OrgOwn> orgOwns = orgOwnService.getOrgOwnsByOrgId(org.getId());
            orgOwns.forEach(orgOwn -> ids.add(orgOwn.getOrg_own_id()));
            map.put(org.getId(),ids);
        });
//        ShardedJedis shardedJedis=shardedJedisPool.getResource();
//        shardedJedis.get("");
//        Jedis jedis=new Jedis("127.0.0.1", 6379);
//        jedis.auth("admin");
//        jedis.hmset("or",map);

    }

    public void test1(){
        // 创建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        // 设置自定义realm
        defaultSecurityManager.setRealm(new CustomerRealm());
        // 设置安全工具类
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        // 通过安全工具类获取subject
        Subject subject = SecurityUtils.getSubject();
        // 创建token
        UsernamePasswordToken token = new UsernamePasswordToken("christy", "123456");
        try {
            // 登录认证
            subject.login(token);
            System.out.println(subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        }
    }

    @Test
    public void test2(){
        List<User> users=new ArrayList<>();
        List<String> familyNames=familyNameService.list().stream().map(FamilyName::getFamilyName).collect(Collectors.toList());
        List<String> chars=chineseCharService.list().stream().map(ChineseChar::getChars).collect(Collectors.toList());
        QueryWrapper<Org> queryWrapper=new QueryWrapper<>();
        queryWrapper.ne("rank","0");
        queryWrapper.ne("rank","1");
        List<Org> orgs=orgMapper.selectList(queryWrapper);
        orgs.forEach(org -> {
            System.out.println(org);
            for(int i=0;i<30;i++){
                users.add(UserUtils.getUser(org,familyNames,chars));
            }
//            users.add(UserUtils.getUser(org,familyNames,chars));
//            System.out.println("用户数量："+users.size());
        });
        userService.saveBatch(users);
    }

    @Test
    public void test3(){
        User user=userMapper.selectById(7363406);
        user.setUsername(UserUtils.getFirstSpell(user.getName())+user.getId());
        userMapper.updateById(user);
        System.out.println(user);
//        users.forEach(user -> {
//            System.gc();
//            user.setUsername(UserUtils.getFirstSpell(user.getName())+user.getId());
//        });
//        userService.saveOrUpdateBatch(users);
    }





    public static void main(String[] args) {


    }

}
