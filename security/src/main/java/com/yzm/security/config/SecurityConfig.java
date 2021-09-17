package com.yzm.security.config;

import com.yzm.security.config.sec1.SecExpiredSessionStrategy;
import com.yzm.security.config.sec1.SecLoginFailureHandler;
import com.yzm.security.config.sec1.SecLoginSuccessHandler;
import com.yzm.security.config.sec1.SecLogoutSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;

    public SecurityConfig(@Qualifier("secUserDetailsServiceImpl") UserDetailsService userDetailsService, DataSource dataSource) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
    }

    /**
     * 密码编码器
     * passwordEncoder.encode是用来加密的,passwordEncoder.matches是用来解密的
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 使用“记住我”功能，默认使用InMemoryTokenRepositoryImpl将token存在内存中
     * 如果使用JdbcTokenRepositoryImpl，会创建persistent_logins数据库表，并把token存入表
     */
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //系统启动时会自动创建表，只需要在第一次系统启动时创建即可，这行代码在需要创建表时才启动
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * 配置用户
     * 指定默认从哪里获取认证用户的信息，即指定一个UserDetailsService接口的实现类
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //配置资源权限规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                // 自定义表单登录
                .formLogin()
                .loginPage("/auth/login") //指定登录页的路径，默认/login
                .loginProcessingUrl("/login") //指定自定义form表单请求的路径(必须跟login.html中的form action=“url”一致)
//                .defaultSuccessUrl("/home", true) //登录成功跳转
//                .failureUrl("/user/login?error")//登录失败跳转
                .successHandler(new SecLoginSuccessHandler()) //登录成功后的处理
                .failureHandler(new SecLoginFailureHandler()) //登录失败后的处理
                .permitAll()
                .and()

                //退出登录：使用默认的退出登录端点/logout退出登录
                .logout()
                .logoutUrl("/signout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(new SecLogoutSuccessHandler())
                .permitAll()
                .and()

                //记住我：使用默认的“记住我”功能，把记住用户已登录的Token保存在内存里，记住30分钟
                .rememberMe()
                //.tokenRepository(getPersistentTokenRepository()) // 将token存到数据库
                //.userDetailsService(userDetailsService)
                .tokenValiditySeconds(1800)
                .and()

                // 访问路径URL的授权策略，如登录、Swagger访问免登录认证等
                .authorizeRequests()
                .antMatchers("/home", "/user/register", "/user/login", "/login/invalid", "/kick").permitAll() //指定url放行
                .antMatchers("/swagger**/**", "/v2/**", "/webjars").permitAll() //swagger文档
                .antMatchers("/druid/**").permitAll() //查看SQL监控（druid）
                .anyRequest().authenticated() //其他任何请求都需要身份认证
                .and()

                //session 管理
                .sessionManagement()
                .invalidSessionUrl("/login/invalid") // Session 已过期 跳转url
                .maximumSessions(1) // 限制最大登陆数
                .maxSessionsPreventsLogin(true) // 当达到最大值时，是否保留已经登录的用户 true：第一个浏览器登录的用户还在，第二个浏览器试图登录但无法登录；false：第一个浏览器登录的用户会提示被迫下线，第二个浏览器试图登录能成功登录
                .expiredSessionStrategy(new SecExpiredSessionStrategy()) // 当达到最大值时，旧用户被踢出后的操作
                .sessionRegistry(sessionRegistry())
        ;
    }

}
