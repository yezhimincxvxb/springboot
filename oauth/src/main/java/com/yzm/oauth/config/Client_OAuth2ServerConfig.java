package com.yzm.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 客户端模式
 */
@Configuration
public class Client_OAuth2ServerConfig {

    private static final String DEMO_RESOURCE_ID = "order";

    /**
     * 授权服务器
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        private final AuthenticationManager authenticationManager;
        private final PasswordEncoder passwordEncoder;

        public AuthorizationServerConfiguration(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
            this.authenticationManager = authenticationManager;
            this.passwordEncoder = passwordEncoder;
        }

        /*
         * 客户端模式，没有用户的概念，直接与认证服务器交互，用配置中的客户端信息去申请accessToken，
         * 客户端有自己的client_id,client_secret对应于用户的username,password，而客户端也拥有自己的authorities，
         * 当采取client模式认证时，对应的权限也就是客户端自己的authorities。
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("oauth_client")
                    .secret(passwordEncoder.encode("123456"))
                    .authorizedGrantTypes("client_credentials", "refresh_token")
                    .resourceIds(DEMO_RESOURCE_ID)
                    .scopes("select")
                    .authorities("client")
            ;
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    // token存到内存
                    .tokenStore(new InMemoryTokenStore())
                    // 增加配置，允许 GET、POST 请求获取 token，即访问端点：oauth/token
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                    .authenticationManager(authenticationManager);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.allowFormAuthenticationForClients();
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
            resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/product/**").authenticated()
                    .antMatchers("/order/**").access("#oauth2.hasScope('select') and hasRole('ROLE_ADMIN')")
            ;
        }
    }

}
