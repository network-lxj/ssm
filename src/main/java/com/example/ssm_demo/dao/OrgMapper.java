package com.example.ssm_demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ssm_demo.entity.Org;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrgMapper extends BaseMapper<Org> {


    List<Org> pageOrg(int page, int size);

    int getCounts(String rank);

}
