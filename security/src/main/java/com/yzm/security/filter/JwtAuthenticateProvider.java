package com.yzm.security.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 身份验证提供者
 * <p>
 * 作用是根据传入的Token中的username调用UserDetailService加载数据库中的认证授权信息(UserDetails)，然后使用PasswordEncoder对比用户登录密码是否正确
 */
public class JwtAuthenticateProvider extends DaoAuthenticationProvider {

    public JwtAuthenticateProvider(UserDetailsService userDetailsService) {
        setUserDetailsService(userDetailsService);
        setPasswordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 可以在此处覆写整个登录认证逻辑
        return super.authenticate(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 可以在此处覆写密码验证逻辑
        super.additionalAuthenticationChecks(userDetails, authentication);
    }

}
