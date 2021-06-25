package com.example.ssm_demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ssm_demo.dao.OrgMapper;
import com.example.ssm_demo.entity.Org;
import com.example.ssm_demo.entity.view.TreeEntity;
import com.example.ssm_demo.enums.AreaRnkEnum;
import com.example.ssm_demo.service.OrgService;
import com.example.ssm_demo.util.Resp;
import org.bouncycastle.pqc.crypto.gmss.Treehash;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements OrgService {

    @Resource
    OrgMapper orgMapper;

    @Override
    public Resp getOrgs(Integer orgId) {
        List<TreeEntity> treeList=new ArrayList<>();
        if(null==orgId){
            orgId=1;
            Org org =  orgMapper.selectById(orgId);
            treeList.add(new TreeEntity(org.getId(),org.getName(),0,true,true));
        }
        List<Org> orgs= selectByPid(orgId);
        orgs.forEach(org1 -> {
            if(org1.getRank().equals(AreaRnkEnum.village.getValue()) ||
                    (org1.getRank().equals(AreaRnkEnum.county.getValue()) && org1.getName().equals("市辖区"))){
                treeList.add(new TreeEntity(org1.getId(),org1.getName(),org1.getPid(),false,false));
            } else {
                treeList.add(new TreeEntity(org1.getId(),org1.getName(),org1.getPid(),false,true));
            }
        });
        return Resp.success(treeList,"success");
    }

//    @Override
//    public Resp getOrgs(Integer orgId) {
//        List<TreeMap<String,Object>> list=new ArrayList<>();
//        if(null==orgId){
//            orgId=1;
//            Org org =  orgMapper.selectById(orgId);
//            TreeMap<String,Object> treeMap=new TreeMap<>();
//            treeMap.put("id",org.getId());
//            treeMap.put("name",org.getName());
//            treeMap.put("open",true);
//            treeMap.put("pid",org.getPid());
//            list.add(treeMap);
//        }
//        //子级
//        List<Org> orgs= getOrgsByPid(orgId);
//        orgs.forEach(org1 -> {
//            TreeMap<String,Object> treeMap1=new TreeMap<>();
//            treeMap1.put("id",org1.getId());
//            treeMap1.put("name",org1.getName());
//            if(org1.getRank().equals(AreaRnkEnum.village.getValue())){
//                treeMap1.put("open",false);
//            }else{
//                treeMap1.put("open",true);
//            }
//            treeMap1.put("pid",org1.getPid());
//            list.add(treeMap1);
//        });
//        System.out.println(JSON.toJSONString(list));
//        return Resp.success(JSON.toJSONString(list),"success");
//    }

    @Override
    public Resp queryOrgs(Integer orgId) {

        QueryWrapper<Org> queryWrapper=new QueryWrapper<>();
        if(null!=orgId){
            queryWrapper.eq("pid",orgId);
        }

        page(new Page<>(),queryWrapper);
        return null;
    }

    @Override
    public List<Org> selectByPid(Integer pid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("pid",pid);
        return orgMapper.selectList(queryWrapper);
    }

    @Override
    public List<Org> selectByRnk(String rank) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("rank",rank);
        return orgMapper.selectList(queryWrapper);
    }

    @Override
    public String getFullName(Org org) {
        List<String> names=new ArrayList<>();
        StringBuffer name=new StringBuffer();
        while (true){
            names.add(org.getName());
            if(org.getPid()==0){
                break;
            }
            org=orgMapper.selectById(org.getPid());
        }
        for(int i=(names.size()-1);i>=0;i--){
            name.append(names.get(i));
        }
        return name.toString();
    }


    public static void main(String[] args) {

    }
}
