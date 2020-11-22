package com.qcstudio.qcfamilyuser.shiro.realm;

import com.qcstudio.qcfamilyuser.entity.User;
import com.qcstudio.qcfamilyuser.service.UserService;
import com.qcstudio.qcfamilyuser.shiro.jwt.JwtCredentialsMatcher;
import com.qcstudio.qcfamilyuser.shiro.jwt.JwtToken;
import com.qcstudio.qcfamilyuser.utils.ApplicationContextUtil;
import com.qcstudio.qcfamilyuser.utils.JwtUtil;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author xgh
 */
public class JwtRealm extends AuthorizingRealm {
    public JwtRealm() {
        // 用我们自定的Matcher
        this.setCredentialsMatcher(new JwtCredentialsMatcher());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        JwtToken jwtToken = (JwtToken) token;
//        String tokenStr = jwtToken.getToken();

        // 取决于JwtToken的getPrincipal()
        String tokenStr = (String) token.getPrincipal();

        // 从jwt字符串中解析出username信息
        String username = JwtUtil.getClaimByKey(tokenStr, "username");
        if (!Strings.isEmpty(username)) {
            UserService userService = (UserService) ApplicationContextUtil.getBean("userService");
            // 根据token中的username去数据库核对信息，返回用户信息，并封装称SimpleAuthenticationInfo给Matcher去校验
            User user = userService.findByUsername(username);
            // principle是身份信息，简单的可以放username，也可以将User对象作为身份信息
            // 身份信息可以在登录成功之后通过subject.getPrinciple()取出
            return new SimpleAuthenticationInfo(user, user.getJwtsalt(), this.getName());
        }

        return null;
    }
}
