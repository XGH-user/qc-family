package com.qcstudio.qcfamilyuser.service;


import com.qcstudio.qcfamilyuser.entity.Permission;

import java.util.List;

/**
 * @author xgh
 */
public interface PermissionService {

    /**
     * 根据角色id查询权限集合
     * 调用RoleDao层，但业务层面，它属于User，可以写在UserService中
     * @param roleId
     * @return
     */
    List<Permission> getPermsByRoleId(int roleId);
}
