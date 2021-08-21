package com.yzm.security.config;

import com.yzm.security.filter.JwtAuthenticateFilter;
import com.yzm.security.filter.JwtAuthenticateProvider;
import com.yzm.security.filter.JwtAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

    //密码编码器
    //passwordEncoder.encode是用来加密的,passwordEncoder.matches是用来解密的
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //配置用户
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义身份认证组件 JwtAuthenticationProvider，并注入 UserDetailsService
        auth.authenticationProvider(new JwtAuthenticateProvider(userDetailsService));
    }

    //配置资源权限规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        http.cors().and().csrf().disable();

        // 访问路径URL的授权策略，如登录、Swagger访问免登录认证等
        http.authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 路径放行
                .antMatchers("/cors").permitAll()
                .antMatchers("/swagger**/**").permitAll()
                // 其他任何请求都需要身份认证
                .anyRequest().authenticated();
        // 默认登录入口
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/login")
//                .successForwardUrl("/loginSuccess");

        // 登录认证过滤器 JwtLoginFilter，由它来触发登录认证
        http.addFilterBefore(new JwtAuthenticateFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        // 访问控制过滤器 JwtAuthenticationFilter，在授权时解析令牌和设置登录状态
        http.addFilterBefore(new JwtAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        // 退出登录处理器，因为是前后端分离，防止内置的登录处理器在后台进行跳转
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 自定义认证策略
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        String password = passwordEncoder().encode("123456");

        log.info("加密后的密码:" + password);

//        auth.inMemoryAuthentication().withUser("admin").password(password)
//                .roles("ADMIN").and();
        auth.inMemoryAuthentication().withUser("user").password(password)
                .roles("USER");
    }

    //忽略静态资源，不会参与认证
    @Override
    public void configure(WebSecurity web) throws Exception {
    }

}
