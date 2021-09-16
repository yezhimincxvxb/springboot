package com.yzm.security.config.sec;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启动方法级别的权限控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final SecPermissionEvaluator customPermissionEvaluator;

    public SecurityConfig(@Qualifier("secUserDetailsServiceImpl") UserDetailsService userDetailsService, SecPermissionEvaluator customPermissionEvaluator) {
        this.userDetailsService = userDetailsService;
        this.customPermissionEvaluator = customPermissionEvaluator;
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
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(customPermissionEvaluator);
        return handler;
    }

    /**
     * 配置用户
     * 指定默认从哪里获取认证用户的信息，即指定一个UserDetailsService接口的实现类
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 从内存创建用户
/*        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
                .roles("ADMIN")
                .authorities("CREATE", "UPDATE", "DELETE", "SELECT")
                .and()
                .withUser("yzm")
                .password(passwordEncoder().encode("123456"))
                .roles("USER")
                .authorities("SELECT")
        ;*/

        // 从数据库获取用户
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(new SecAuthenticationProvider(userDetailsService, passwordEncoder()));
    }

    //配置资源权限规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 禁用 csrf, 由于使用的是JWT，我们这里不需要   csrf
                .cors().and()
                .csrf().disable()
        ;

        http
                //表单登录：使用默认的表单登录页面和登录端点/login进行登录
                .formLogin()
                .loginPage("/user/login") //指定登录页的路径，默认/login
                .loginProcessingUrl("/login") //指定自定义form表单请求的路径(必须跟login.html中的form action=“url”一致)
                .permitAll()
                .and()
                // 异常处理
                .exceptionHandling()
                .accessDeniedPage("/login?error")
                .and()
                // 访问路径URL的授权策略，如登录、Swagger访问免登录认证等
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() //跨域预检请求
                .antMatchers("/home", "/user/register", "/user/login").permitAll() //指定url放行
                .antMatchers("/swagger**/**", "/v2/**", "/webjars/**").permitAll() //swagger文档
                .antMatchers("/druid/**").permitAll() //查看SQL监控（druid）
                .anyRequest().authenticated() //其他任何请求都需要身份认证
        ;

        http
                // 登录认证过滤器
                .addFilterBefore(new SecAuthenticateFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                // 访问鉴权过滤器
                .addFilterBefore(new SecAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                // 退出登录处理器，因为是前后端分离，防止内置的登录处理器在后台进行跳转
                .logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
        ;
    }
}
