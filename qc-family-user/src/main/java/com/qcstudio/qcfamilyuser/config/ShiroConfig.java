package com.qcstudio.qcfamilyuser.config;

import com.qcstudio.qcfamilyuser.shiro.jwt.JwtAuthenticatingFilter;
import com.qcstudio.qcfamilyuser.shiro.realm.JwtRealm;
import com.qcstudio.qcfamilyuser.shiro.realm.LoginRealm;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xgh
 * 用来整合shiro框架的配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilter,用来拦截所有请求
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(
            DefaultWebSecurityManager defaultWebSecurityManager, ShiroFilterChainDefinition chainDefinition) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        // 注册我们写的过滤器
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("jwtAuth", new JwtAuthenticatingFilter());
        shiroFilterFactoryBean.setFilters(filters);

        // 设置请求的过滤规则。其中过滤规则中用到了我们注册的过滤器：jwtAuth
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chainDefinition.getFilterChainMap());

        return shiroFilterFactoryBean;
    }

    /**
     * 设置请求的过滤规则
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        // login不做认证，noSessionCreation的作用是用户在操作session时会抛异常
        chainDefinition.addPathDefinition("/user/register", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/user/login", "noSessionCreation,anon");

        // 注意第2个参数的"jwtAuth"需要与上面的 filters.put("jwtAuth", new JwtAuthenticatingFilter()); 一致
        // 做用户认证，permissive参数的作用是当token无效时也允许请求访问，不会返回鉴权未通过的错误
        chainDefinition.addPathDefinition("/user/logout", "noSessionCreation,jwtAuth[permissive]");
        // 默认进行用户鉴权
        chainDefinition.addPathDefinition("/**", "noSessionCreation,jwtAuth");
        return chainDefinition;
    }

    /**
     * 创建安全管理器
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Authenticator authenticator){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 给安全管理器设置自定义的Realm
//        defaultWebSecurityManager.setRealm(realm);
        defaultWebSecurityManager.setAuthenticator(authenticator);
        return defaultWebSecurityManager;
    }

    /**
     * 初始化Authenticator，将我们需要的Realm设置进去
     * Shiro会将Authenticator设置到SecurityManager里面
     */
    @Bean
    public Authenticator authenticator(@Qualifier("loginRealm") Realm loginRealm, @Qualifier("jwtRealm") Realm jwtRealm) {

        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        //设置两个Realm，一个用于用户登录验证和访问权限获取；一个用于jwt token的认证
        authenticator.setRealms(Arrays.asList(loginRealm, jwtRealm));
        //设置多个realm认证策略，一个成功即跳过其它的
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    /**
     * 创建自定义的realm
     * @return
     *
     */
    @Bean("loginRealm")
    public Realm loginRealm(EhCacheManager ehCacheManager){
        LoginRealm loginRealm = new LoginRealm();

        // 修改凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 设置加密算法为MD5
        credentialsMatcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        // 设置散列次数
        credentialsMatcher.setHashIterations(2048);
        loginRealm.setCredentialsMatcher(credentialsMatcher);

        // AuthenticatingRealm里面的isAuthenticationCachingEnabled()
        loginRealm.setCacheManager(ehCacheManager);
        loginRealm.setCachingEnabled(true); // 这句话不能少！！！
        loginRealm.setAuthenticationCachingEnabled(true); // 认证缓存
        loginRealm.setAuthorizationCachingEnabled(true); // 授权缓存

        return loginRealm;
    }

    @Bean("jwtRealm")
    public Realm jwtRealm(EhCacheManager ehCacheManager) {
        JwtRealm jwtRealm = new JwtRealm();

        jwtRealm.setCacheManager(ehCacheManager);
        jwtRealm.setCachingEnabled(true);  // 这句话不能少！！！
        jwtRealm.setAuthenticationCachingEnabled(true); // 认证缓存
        jwtRealm.setAuthorizationCachingEnabled(true); // 授权缓存

        return jwtRealm;
    }

    /**
     * shiro的全局缓存管理器
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        return new EhCacheManager();
    }

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
     * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session，
     * 如果要完全禁用，要配合上过滤规则的noSessionCreation的Filter来实现
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }
}
