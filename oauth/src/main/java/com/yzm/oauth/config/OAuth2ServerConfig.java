//package com.yzm.oauth.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
//@Configuration
//public class OAuth2ServerConfig {
//
//    private static final String DEMO_RESOURCE_ID = "order";
//
//    /**
//     * 授权服务器
//     */
//    @Configuration
//    @EnableAuthorizationServer
//    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//
//        private final AuthenticationManager authenticationManager;
//        private final PasswordEncoder passwordEncoder;
//
//        public AuthorizationServerConfiguration(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
//            this.authenticationManager = authenticationManager;
//            this.passwordEncoder = passwordEncoder;
//        }
//
//        @Override
//        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//            clients.inMemory()
//                    /*
//                     * 客户端模式，没有用户的概念，直接与认证服务器交互，用配置中的客户端信息去申请accessToken，
//                     * 客户端有自己的client_id,client_secret对应于用户的username,password，而客户端也拥有自己的authorities，
//                     * 当采取client模式认证时，对应的权限也就是客户端自己的authorities。
//                     */
//                    .withClient("client_1")
//                    .secret(passwordEncoder.encode("123456"))
//                    .authorizedGrantTypes("client_credentials", "refresh_token")
//                    .resourceIds(DEMO_RESOURCE_ID)
//                    .scopes("select")
//                    .authorities("client")
//                    .and()
//                    /*
//                     * 密码模式，自己本身有一套用户体系，在认证时需要带上自己的用户名和密码，以及客户端的client_id,client_secret。
//                     * 此时，accessToken所包含的权限是用户本身的权限，而不是客户端的权限
//                     */
//                    .withClient("client_2")
//                    .secret(passwordEncoder.encode("123456"))
//                    .authorizedGrantTypes("password", "refresh_token")
//                    .resourceIds(DEMO_RESOURCE_ID)
//                    .scopes("select")
//                    .authorities("client")
//            ;
//        }
//
//        @Override
//        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//            endpoints
//                    //.tokenStore(new RedisTokenStore(redisConnectionFactory))
//                    //.tokenStore(new InMemoryTokenStore())
//                    //.userDetailsService(userDetailsService)
//                    // 2018-4-3 增加配置，允许 GET、POST 请求获取 token，即访问端点：oauth/token
//                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
//                    .authenticationManager(authenticationManager);
//        }
//
//        /**
//         * 权限控制
//         */
//        @Override
//        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//            security
//                    .allowFormAuthenticationForClients();
//        }
//    }
//
//    /**
//     * 资源服务器
//     */
//    @Configuration
//    @EnableResourceServer
//    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//        @Override
//        public void configure(ResourceServerSecurityConfigurer resources) {
//            //如果关闭 stateless，则 accessToken 使用时的 session id 会被记录，后续请求不携带 accessToken 也可以正常响应
//            resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
//        }
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            http
//                    .csrf().disable()
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                    .and().anonymous()
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers("/product/**").access("#oauth2.hasScope('select') and hasRole('ROLE_ADMIN')")
//                    .antMatchers("/order/**").authenticated();//配置访问控制，必须认证过后才可以访问
//        }
//    }
//
//}
