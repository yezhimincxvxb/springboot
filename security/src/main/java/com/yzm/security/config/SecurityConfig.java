package com.yzm.security.config;

import com.yzm.security.jwt.JwtAuthenticateFilter;
import com.yzm.security.jwt.JwtAuthenticationEntryPoint;
import com.yzm.security.jwt.JwtAuthorizationFilter;
import lombok.extern.slf4j.Slf4j;
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

    //密码编码器
    //passwordEncoder.encode是用来加密的,passwordEncoder.matches是用来解密的
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //配置用户
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //配置资源权限规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用 csrf, 由于使用的是JWT，我们这里不需要   csrf
        http.cors().and().csrf().disable()
                //基于Token，因为不需要Session;设置 session 状态 STATELESS 无状态
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        // 访问路径URL的授权策略，如登录、Swagger访问免登录认证等
        http.authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 路径放行
                .antMatchers("/swagger**/**").permitAll() //swagger文档
                .antMatchers("/v2/**").permitAll() //swagger文档
                .antMatchers("/druid/**").permitAll() //查看SQL监控（druid）
                // 其他任何请求都需要身份认证
                .anyRequest().authenticated()
                // 使用默认的“记住我”功能，把记住用户已登录的Token保存在内存里，记住30分钟
                .and()
                .rememberMe()
                .tokenValiditySeconds(1800)
        ;

        // 登录认证过滤器
        http.addFilterBefore(new JwtAuthenticateFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        // 访问鉴权过滤器
        http.addFilterBefore(new JwtAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        // 异常处理 匿名用户访问无权限资源时的异常
        http.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());

        // 退出登录处理器，因为是前后端分离，防止内置的登录处理器在后台进行跳转
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    //Web层面的配置，一般用来配置无需安全检查的路径
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("**.js",
                        "**.css",
                        "/images/**",
                        "/webjars/**",
                        "/**/favicon.ico",
                        "/swagger-ui.html"
                );
    }

}
