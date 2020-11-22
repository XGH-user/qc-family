package com.qcstudio.qcfamilyuser.shiro.realm;

import com.qcstudio.qcfamilyuser.entity.Permission;
import com.qcstudio.qcfamilyuser.entity.Role;
import com.qcstudio.qcfamilyuser.entity.User;
import com.qcstudio.qcfamilyuser.service.PermissionService;
import com.qcstudio.qcfamilyuser.service.RoleService;
import com.qcstudio.qcfamilyuser.service.UserService;
import com.qcstudio.qcfamilyuser.utils.ApplicationContextUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author xgh
 */
public class LoginRealm extends AuthorizingRealm {

    /**
     * 可以往Shiro中注册多种Realm。某种Token对象需要对应的Realm来处理。
     * 复写该方法表示该方法支持处理哪一种Token
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取身份信息
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();

        // 从容器中获取UserService组件
        UserService userService = (UserService) ApplicationContextUtil.getBean("userService");
        // 从容器中获取PermissionService组件
        PermissionService permissionService = (PermissionService) ApplicationContextUtil.getBean("permissionService");
        List<Role> roles = userService.getRolesByUsername(primaryPrincipal);

        if (!CollectionUtils.isEmpty(roles)) {
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            for (Role role : roles) {
//                System.out.println("---: " + role.getId() + "----" + role.getRoleName());
                authorizationInfo.addRole(role.getName());

                // 查询当前角色的权限信息
                List<Permission> perms = permissionService.getPermsByRoleId(role.getId());
                if (!CollectionUtils.isEmpty(perms)) {
                    for (Permission perm : perms) {
                        authorizationInfo.addStringPermission(perm.getName());
                    }
                }

            }
            return authorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 从Token中获取身份信息。这里实际上是username，这里从UsernamePasswordToken的源码可以看出
        String principal = (String) token.getPrincipal();
        // 从IOC容器中获取UserService组件
        UserService userService = (UserService) ApplicationContextUtil.getBean("userService");

        User user = userService.findByUsername(principal);

        if (!ObjectUtils.isEmpty(user)) {
            // 返回正确的信息（数据库存储的），作为比较的基准
            return  new SimpleAuthenticationInfo(
                    user, user.getPassword(),
                    ByteSource.Util.bytes(user.getSalt()), this.getName()
            );
        }
        return null;
    }
}
