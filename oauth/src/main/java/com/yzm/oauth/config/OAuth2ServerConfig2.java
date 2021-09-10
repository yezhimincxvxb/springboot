package com.yzm.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.Assert;

import javax.sql.DataSource;

@Configuration
public class OAuth2ServerConfig2 {

    private static final String QQ_RESOURCE_ID = "qq";
    public static final String AUTH_CODE = "authCode";
    public static final String AUTH_CODE2 = "client_code";
    public static final String PASSWORD = "123456";
    public static final String CODE_REDIRECT = "code/redirect";
    public static final String CODE_REDIRECT2 = "code/redirect2";
    public static final String EASY_REDIRECT = "easy/redirect";
    //accessToken 过期
    public static final int accessTokenValiditySecond = 60 * 60 * 2; //2小时
    public static final int refreshTokenValiditySecond = 60 * 60 * 24 * 7; // 7 天

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

        @Autowired
        private DataSource dataSource;

//        @Autowired
//        private RedisConnectionFactory redisConnectionFactory;

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey("jwtSigningKey");
            return jwtAccessTokenConverter;
        }

        @Bean
        public ClientDetailsService clientDetails() {
            return new JdbcClientDetailsService(dataSource);
        }

        /**
         * token存储方式
         */
        @Bean
        public TokenStore tokenStore() {
            // 默认内存
            // return new InMemoryTokenStore();
            // 数据库
             return new JdbcTokenStore(dataSource);
            // redis
            // return new RedisTokenStore(redisConnectionFactory);
            // jwt
            // return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * a configurer that defines the client details service.
         * Client details can be initialized, or you can just refer to an existing store.
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    /*
                        授权码模式
                     */
                    .withClient(AUTH_CODE)
                    .secret(passwordEncoder.encode(PASSWORD))
                    .resourceIds(QQ_RESOURCE_ID)
                    .authorizedGrantTypes("authorization_code", "refresh_token")
                    .authorities("ROLE_CLIENT")
                    .scopes("get_user_info", "get_fans_list")
                    .redirectUris("http://localhost:8080/qq/" + CODE_REDIRECT)
                    .autoApprove(true)
                    .autoApprove("get_user_info")
                    .accessTokenValiditySeconds(accessTokenValiditySecond)
                    .refreshTokenValiditySeconds(refreshTokenValiditySecond)
                    /*
                        简化模式
                     */
                    .and()
                    .withClient("easy")
                    .secret(passwordEncoder.encode(PASSWORD))
                    .resourceIds(QQ_RESOURCE_ID)
                    .authorizedGrantTypes("implicit")
                    .authorities("ROLE_CLIENT")
                    .scopes("get_user_info")
                    .redirectUris("http://localhost:8080/qq/" + EASY_REDIRECT)
            ;
            // 读取数据库字段配置客户端 (跟内存二选一)
            // clients.withClientDetails(clientDetails());
        }

        /**
         * defines the security constraints on the token endpoint.
         */
        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security
//                    .tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
//                    .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
                    .tokenKeyAccess("permitAll()")
                    .checkTokenAccess("permitAll()")
                    .realm(QQ_RESOURCE_ID)
                    .allowFormAuthenticationForClients();
        }

        /**
         * defines the authorization and token endpoints and the token services.
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(tokenStore())
                    // 使用jwt时，开启
                    // .accessTokenConverter(jwtAccessTokenConverter())
                    // 允许 GET、POST 请求获取 token，即访问端点：oauth/token
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                    // 刷新令牌授权将包含对用户详细信息的检查，以确保该帐户仍然处于活动状态
                    .userDetailsService(userDetailsService)
                    .authenticationManager(authenticationManager);
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
                    .antMatchers("/qq/code/redirect*", "/qq/easy/redirect").permitAll() // 放行重定向接口
                    .antMatchers("/qq/info/**").access("#oauth2.hasScope('get_user_info')")
                    .antMatchers("/qq/info2/**").access("#oauth2.hasScope('get_user_info') and hasAnyRole('ROLE_ADMIN')")
                    .anyRequest().authenticated()
            ;
        }
    }

}
