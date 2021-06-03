package com.example.ssm_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ssm_demo.dao.OrgMapper;
import com.example.ssm_demo.entity.Org;
import com.example.ssm_demo.service.OrgService;
import org.springframework.stereotype.Service;

@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements OrgService {
}
