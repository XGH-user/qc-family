package com.qcstudio.qcfamilyuser.service.impl;

import com.qcstudio.qcfamilyuser.entity.Permission;
import com.qcstudio.qcfamilyuser.mapper.PermissionMapper;
import com.qcstudio.qcfamilyuser.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xgh
 */
@Service("permissionService")
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 根据角色id查询权限集合
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Permission> getPermsByRoleId(int roleId) {
        return this.permissionMapper.selectPermissionsByRoleId(roleId);
    }
}
