package com.qcstudio.qcfamilyuser.service;

import com.qcstudio.qcfamilyuser.entity.Role;
import com.qcstudio.qcfamilyuser.entity.User;

import java.util.List;

/**
 * @author xgh
 */
public interface UserService {

    /**
     * 用户注册
     * @param user
     */
    void register(User user);

    /**
     * 根据username更新jwt密钥
     * @param user
     * @return
     */
    String generateJwt(User user);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据username查找角色
     * @param username
     * @return
     */
    List<Role> getRolesByUsername(String username);

    /**
     * 用户登出
     * @param user
     */
    void logout(User user);
}
