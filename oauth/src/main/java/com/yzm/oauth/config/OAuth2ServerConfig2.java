package com.yzm.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2ServerConfig2 {

    private static final String QQ_RESOURCE_ID = "qq";

    /**
     * 授权服务器
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        private final AuthenticationManager authenticationManager;
        private final PasswordEncoder passwordEncoder;
        private final UserDetailsService userDetailsService;

        public AuthorizationServerConfiguration(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
            this.authenticationManager = authenticationManager;
            this.passwordEncoder = passwordEncoder;
            this.userDetailsService = userDetailsService;
        }

        @Bean
        public ApprovalStore approvalStore() {
            TokenApprovalStore store = new TokenApprovalStore();
            store.setTokenStore(tokenStore());
            return store;
        }

        @Bean
        public TokenStore tokenStore() {
            return new InMemoryTokenStore();
            // 需要使用 redis 的话，放开这里
//            return new RedisTokenStore(redisConnectionFactory);
        }

        //accessToken 过期
        private static final int accessTokenValiditySecond = 60 * 60 * 2; //2小时
        private static final int refreshTokenValiditySecond = 60 * 60 * 24 * 7; // 7 天


        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    /*
                     * 授权码模式
                     */
                    .withClient("code")
                    .secret(passwordEncoder.encode("123456"))
                    .resourceIds(QQ_RESOURCE_ID)
                    .authorizedGrantTypes("authorization_code", "refresh_token")
                    .authorities("ROLE_CLIENT")
                    .scopes("get_user_info", "get_fans_list")
                    .redirectUris("http://localhost:8080/qq/code/redirect")
                    .autoApprove(true)
                    .autoApprove("get_user_info")
                    .accessTokenValiditySeconds(accessTokenValiditySecond)
                    .refreshTokenValiditySeconds(refreshTokenValiditySecond)
                    /*
                     * 简化模式
                     */
                    .and()
                    .withClient("easy")
                    .secret(passwordEncoder.encode("123456"))
                    .resourceIds(QQ_RESOURCE_ID)
                    .authorizedGrantTypes("implicit")
                    .authorities("ROLE_CLIENT")
                    .scopes("get_user_info")
                    .redirectUris("http://localhost:8080/qq/easy/redirect")
            ;
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(new InMemoryTokenStore())
                    .approvalStore(approvalStore())
                    //允许 GET、POST 请求获取 token，即访问端点：oauth/token
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                    //刷新token
                    .userDetailsService(userDetailsService)
                    .authenticationManager(authenticationManager);
        }

        /**
         * 权限控制
         */
        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security
                    .tokenKeyAccess("permitAll()")
                    .checkTokenAccess("isAuthenticated()")
//                    .realm(QQ_RESOURCE_ID)
                    .allowFormAuthenticationForClients();
        }

    }

    /**
     * 资源服务器
     */
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            //如果关闭 stateless，则 accessToken 使用时的 session id 会被记录，后续请求不携带 accessToken 也可以正常响应
            //设置为true则每次请求都必须携带accessToken
            resources.resourceId(QQ_RESOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    // 保险起见，防止被主过滤器链路拦截
                    .requestMatchers().antMatchers("/qq/**")
                    .and()
                    .authorizeRequests()
                    // 放行重定向接口
                    .antMatchers("/qq/code/redirect").permitAll()
                    .antMatchers("/qq/easy/redirect").permitAll()
                    .antMatchers("/qq/info/**").access("#oauth2.hasScope('get_user_info')")
                    .anyRequest().authenticated()
            ;
        }
    }

}
