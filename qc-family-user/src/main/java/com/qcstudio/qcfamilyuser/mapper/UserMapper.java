package com.qcstudio.qcfamilyuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcstudio.qcfamilyuser.entity.Role;
import com.qcstudio.qcfamilyuser.entity.User;


import java.util.List;

/**
 * @author xgh
 * 成员Mapper
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据角色id查询用户
     * @param roleId
     * @return
     */
    List<User> selectUsersByRoleId(int roleId);
}
