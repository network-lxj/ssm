package com.example.ssm_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ssm_demo.entity.Org;
import com.example.ssm_demo.util.Resp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrgService extends IService<Org> {

    Resp getOrgs(Integer orgId);

    Resp queryOrgs(Integer orgId);

    List<Org> selectByPid(Integer pid);

    List<Org> selectByRnk(String rank);

    String getFullName(Org org);
}
