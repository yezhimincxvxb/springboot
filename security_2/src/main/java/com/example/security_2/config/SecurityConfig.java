package com.example.security_2.config;

import com.example.security_2.jwt.JwtAuthenticateFilter;
import com.example.security_2.jwt.JwtAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启动方法级别的权限控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public final UserDetailsService userDetailsService;

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 密码编码器
     * passwordEncoder.encode是用来加密的,passwordEncoder.matches是用来解密的
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 配置用户
     * 指定默认从哪里获取认证用户的信息，即指定一个UserDetailsService接口的实现类
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(new JwtAuthenticateProvider(userDetailsService, passwordEncoder()));
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //配置资源权限规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 禁用 csrf, 由于使用的是JWT，我们这里不需要   csrf
                .cors().and()
                .csrf().disable()
                //基于Token，因为不需要Session;设置 session 状态 STATELESS 无状态
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;

        http
                //表单登录：使用默认的表单登录页面和登录端点/login进行登录
                .formLogin()
                .loginPage("/user/login") //指定登录页的路径，默认/login
                .loginProcessingUrl("/auth/login") //指定自定义form表单请求的路径
                .failureForwardUrl("/loginFail")//登录失败跳转
                .successForwardUrl("/toHome") //登录成功跳转
                .permitAll()
                .and()
                //退出登录：使用默认的退出登录端点/logout退出登录
                .logout()
                .permitAll()
                .and()
                //记住我：使用默认的“记住我”功能，把记住用户已登录的Token保存在内存里，记住30分钟
                .rememberMe()
                .tokenValiditySeconds(1800)
                .and()
                // 访问路径URL的授权策略，如登录、Swagger访问免登录认证等
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() //跨域预检请求
                .antMatchers("/toHome", "user/login").permitAll() //指定url放行
                .antMatchers("/swagger**/**", "/v2/**").permitAll() //swagger文档
                .antMatchers("/druid/**").permitAll() //查看SQL监控（druid）
                .anyRequest().authenticated() //其他任何请求都需要身份认证
        ;

        http
                // 登录认证过滤器
                .addFilterBefore(new JwtAuthenticateFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                // 访问鉴权过滤器
                .addFilterBefore(new JwtAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                // 异常处理
//                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
//                .and()
                // 退出登录处理器，因为是前后端分离，防止内置的登录处理器在后台进行跳转
                .logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    /**
     * 在内存中创建用户并配置角色
     * 注意：一个内存中只能同时有一个用户
     * 正常情况该配置是不需要的
     */
//    @Autowired
//    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        String password = passwordEncoder().encode("123456");
//        auth.inMemoryAuthentication().withUser("admin").password(password).roles("ADMIN").and();
//    }

    //Web层面的配置，一般用来配置无需安全检查的路径
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("**.js", "**.css",
//                        "/images/**",
//                        "/webjars/**",
//                        "/**/favicon.ico",
//                        "/swagger-ui.html", "/v2/**"
//                );
//    }

}
