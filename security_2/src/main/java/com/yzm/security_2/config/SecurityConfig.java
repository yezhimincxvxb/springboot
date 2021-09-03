package com.yzm.security_2.config;

import com.yzm.security_2.constant.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启动方法级别的权限控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, DataSource dataSource) {
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
                //表单登录：使用默认的表单登录页面和登录端点/login进行登录
                .formLogin()
                .loginPage(SysConstant.LOGIN_PAGE_URL) //指定登录页的路径，默认/login
                .loginProcessingUrl(SysConstant.FORM_LOGIN_URL) //指定自定义form表单请求的路径(必须跟login.html中的form action=“url”一致)
                .successForwardUrl(SysConstant.SUCCESS_URL) //登录成功跳转
                .failureForwardUrl(SysConstant.FAIL_URL)//登录失败跳转
                .permitAll()
                .and()

                //退出登录：使用默认的退出登录端点/logout退出登录
                .logout()
                .permitAll()
                .and()

                //记住我：使用默认的“记住我”功能，把记住用户已登录的Token保存在内存里，记住30分钟
                .rememberMe()
                .tokenRepository(getPersistentTokenRepository()) // 将token存到数据库
                .tokenValiditySeconds(1800)
                .and()

                // 访问路径URL的授权策略，如登录、Swagger访问免登录认证等
                .authorizeRequests()
                .antMatchers("/toHome", "user/login").permitAll() //指定url放行
                .antMatchers("/swagger**/**", "/v2/**").permitAll() //swagger文档
                .antMatchers("/druid/**").permitAll() //查看SQL监控（druid）
                .anyRequest().authenticated() //其他任何请求都需要身份认证
                .and().csrf().disable()
        ;
    }

}
