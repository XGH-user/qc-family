package com.qcstudio.qcfamilyuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qcstudio.qcfamilyuser.entity.Role;
import com.qcstudio.qcfamilyuser.mapper.RoleMapper;
import com.qcstudio.qcfamilyuser.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xgh
 */
@Service("RoleService")
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


}
