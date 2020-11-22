package com.qcstudio.qcfamilyuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qcstudio.qcfamilyuser.entity.Role;
import com.qcstudio.qcfamilyuser.entity.User;
import com.qcstudio.qcfamilyuser.exceptions.UserExistException;
import com.qcstudio.qcfamilyuser.mapper.RoleMapper;
import com.qcstudio.qcfamilyuser.mapper.UserMapper;
import com.qcstudio.qcfamilyuser.service.UserService;
import com.qcstudio.qcfamilyuser.utils.JwtUtil;
import com.qcstudio.qcfamilyuser.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xgh
 */
@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 用户注册
     * @param user
     */
    @Override
    public void register(User user) {

        QueryWrapper<User> wrapper = new QueryWrapper<User>() ;
        wrapper.eq("username",user.getUsername());
        User u = this.userMapper.selectOne(wrapper);

        if(null != u){
            throw new UserExistException();
        } else {
            String salt = SaltUtils.getSalt(10);
            user.setSalt(salt);
            String jwtsalt = SaltUtils.getSalt(10);
            user.setJwtsalt(jwtsalt);
            Md5Hash md5Hash = new Md5Hash(user.getPassword(), user.getSalt(), 2048);
            user.setPassword(md5Hash.toHex());
            this.userMapper.insert(user);
        }
    }

    /**
     * 根据username更新jwt密钥
     *
     * @param user
     */
    @Override
    public String generateJwt(User user) {
        // 10个字符的随机字符串，作为生成jwt的随机盐
        // 保证每次登录成功返回的Token都不一样
        String Jwtsalt = SaltUtils.getSalt(10);
        // 将此次登录成功的jwt salt存到数据库，下次携带jwt时解密需要使用
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("username",user.getUsername()).set("jwtsalt",Jwtsalt);
        this.userMapper.update(user,wrapper);
        return JwtUtil.generateJwt(user.getUsername(), Jwtsalt);
    }

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return this.userMapper.selectOne(wrapper);
    }

    /**
     * 根据用户名查询角色
     *
     * @param primaryPrincipal
     * @return
     */
    @Override
    public List<Role> getRolesByUsername(String primaryPrincipal) {
        User user = this.findByUsername(primaryPrincipal);
        Integer id = user.getId();
        return this.roleMapper.selectRolesByUserId(id);
    }

    /**
     * 用户登出
     *
     * @param user
     */
    @Override
    public void logout(User user) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("username", user.getUsername()).set("jwtsalt","");
        this.userMapper.update(user,wrapper);
    }
}
