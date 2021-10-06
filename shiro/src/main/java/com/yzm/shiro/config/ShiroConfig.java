package com.yzm.shiro.config;

import com.yzm.shiro.config.filter.AnyRolesAuthFilter;
import com.yzm.shiro.config.filter.JwtAuthFilter;
import com.yzm.shiro.service.PermissionsService;
import com.yzm.shiro.service.RoleService;
import com.yzm.shiro.service.UserService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Configuration
public class ShiroConfig {

    private final UserService userService;
    private final RoleService roleService;
    private final PermissionsService permissionsService;

    public ShiroConfig(UserService userService, RoleService roleService, PermissionsService permissionsService) {
        this.userService = userService;
        this.roleService = roleService;
        this.permissionsService = permissionsService;
    }

    /**
     * 数据库用户认证、鉴权
     */
    @Bean(name = "simpleRealm")
    public SimpleShiroRealm simpleShiroRealm() {
        SimpleShiroRealm simpleShiroRealm = new SimpleShiroRealm(userService, roleService, permissionsService);
        // 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        simpleShiroRealm.setAuthenticationCachingEnabled(false);
        // 启用授权缓存，即缓存AuthorizationInfo信息，默认false
        simpleShiroRealm.setAuthorizationCachingEnabled(false);
        // 凭证匹配器 密码加密解密
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.ALGORITHM_NAME);
        hashedCredentialsMatcher.setHashIterations(PasswordHelper.HASH_ITERATIONS);
        simpleShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return simpleShiroRealm;
    }

    /**
     * jwt认证
     */
    @Bean(name = "jwtRealm")
    public JwtShiroRealm jwtShiroRealm() {
        JwtShiroRealm jwtShiroRealm = new JwtShiroRealm(userService);
        jwtShiroRealm.setCredentialsMatcher(new JwtCredentialsMatcher());
        return jwtShiroRealm;
    }

    /**
     * 记住我功能
     */
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        Cookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(10 * 60); // 10分钟
        cookieRememberMeManager.setCookie(cookie);
        return cookieRememberMeManager;
    }

    /**
     * 权限管理
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 配置单个realm
        //securityManager.setRealm(dbShiroRealm());

        // 配置多个realm，一个用于用户登录验证和访问权限获取；一个用于jwt token的认证
        // 设置多个realm认证策略，一个成功即跳过其它的
        Collection<Realm> realmList = Arrays.asList(simpleShiroRealm(), jwtShiroRealm());
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        authenticator.setRealms(realmList);
        securityManager.setRealms(realmList);
        securityManager.setAuthenticator(authenticator);

        //Cookie
        securityManager.setRememberMeManager(cookieRememberMeManager());

        // 自定义缓存实现 如redis
        // securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        return securityManager;
    }

    /**
     * 启动 @RequiresPermissions、@RequiresRoles注解功能
     */
    @Bean
    //@DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * 启动 @RequiresPermissions、@RequiresRoles注解功能
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证。
     * 需要注意的是，如果用户代码里调用Subject.getSession()还是可以用session
     * 如果要完全禁用，要配合下面的noSessionCreation的Filter来实现
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    /**
     * 设置拦截url
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置无权限时跳转的 url
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        // 添加自定义拦截规则
        Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
        filterMap.put("authToken", new JwtAuthFilter());
        filterMap.put("anyRole", new AnyRolesAuthFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/register", "anon");
        definition.addPathDefinition("/doLogin", "anon");
        definition.addPathDefinition("/logout", "logout");
        definition.addPathDefinition("/info", "noSessionCreation,authToken[permissive]");
        definition.addPathDefinition("/admin/**", "authToken,roles[ADMIN]");
        definition.addPathDefinition("/admin/select", "perms[select]");
        definition.addPathDefinition("/admin/delete", "perms[delete]");
        definition.addPathDefinition("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(definition.getFilterChainMap());
        return shiroFilterFactoryBean;
    }

}
