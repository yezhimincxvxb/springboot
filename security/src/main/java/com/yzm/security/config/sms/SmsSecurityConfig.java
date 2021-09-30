package com.yzm.security.config.sms;

import com.yzm.security.config.SecPermissionEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SmsSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    private final SecPermissionEvaluator permissionEvaluator;

    public SmsSecurityConfig(SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig, SecPermissionEvaluator permissionEvaluator) {
        this.smsCodeAuthenticationSecurityConfig = smsCodeAuthenticationSecurityConfig;
        this.permissionEvaluator = permissionEvaluator;
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
        handler.setPermissionEvaluator(permissionEvaluator);
        return handler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .apply(smsCodeAuthenticationSecurityConfig)

                .and()
                .formLogin()
                .loginPage("/auth/login")
                .permitAll()

                .and()
                .logout().permitAll()

                .and()
                .authorizeRequests()
                .antMatchers("/home", "/sms/code").permitAll() //指定url放行
                .antMatchers("/auth/getVerifyCode").permitAll() //验证码
                .anyRequest().authenticated() //其他任何请求都需要身份认证
        ;
    }

}
