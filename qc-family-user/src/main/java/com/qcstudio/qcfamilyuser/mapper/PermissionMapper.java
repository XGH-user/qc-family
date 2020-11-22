package com.qcstudio.qcfamilyuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcstudio.qcfamilyuser.entity.Permission;
import com.qcstudio.qcfamilyuser.entity.Role;

import java.util.List;

/**
 * @author xgh
 * 权限Mapper
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据角色id查询权限
     * @param roleId
     * @return
     */
    List<Permission> selectPermissionsByRoleId(int roleId);
}
