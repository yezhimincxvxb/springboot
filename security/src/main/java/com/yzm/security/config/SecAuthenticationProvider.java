package com.yzm.security.config;

import com.yzm.security.utils.HttpUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;

/**
 * 调用UserDetailsService获取到数据库中存储的用户信息(UserDetails)
 * 然后通过passwordEncoder密码编码器对UsernamePasswordAuthenticationToken中的密码和UserDetails中的密码进行比较
 */
public class SecAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 可以覆写整个登录认证逻辑
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 校验验证码
        HttpServletRequest request = HttpUtils.getHttpServletRequest();
        String verifyCode = request.getParameter("verifyCode");
        String validateCode = (String) request.getSession().getAttribute("validateCode");
        if (!validateCode.equalsIgnoreCase(verifyCode)) {
            throw new DisabledException("verifyCode is wrong");
        }

        // 校验用户信息
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Bad password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 这里不要忘记，和UsernamePasswordAuthenticationToken比较
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
