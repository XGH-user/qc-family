package com.qcstudio.qcfamilyuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcstudio.qcfamilyuser.entity.Permission;
import com.qcstudio.qcfamilyuser.entity.Role;
import com.qcstudio.qcfamilyuser.entity.User;


import java.util.List;

/**
 * @author xgh
 * 角色Mapper
 */

public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查找角色
     * @param userId
     * @return
     */
    List<Role> selectRolesByUserId(int userId);

    /**
     * 根据权限id查找角色
     * @param permissionId
     * @return
     */
    List<Role> selectRolesByPermissionId(int permissionId);
}
