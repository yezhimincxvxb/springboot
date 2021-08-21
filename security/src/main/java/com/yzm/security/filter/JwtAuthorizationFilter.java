package com.yzm.security.filter;

import com.yzm.security.entity.JwtAuthenticationToken;
import com.yzm.security.entity.JwtGrantedAuthority;
import com.yzm.security.entity.JwtUserDetails;
import com.yzm.security.service.UserService;
import com.yzm.security.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限验证过滤器
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 检查登录状态
        checkAuthentication(request);
        chain.doFilter(request, response);
    }

    private void checkAuthentication(HttpServletRequest request) {
        try {
            String token = JwtTokenUtils.getTokenFromRequest(request);
            if (StringUtils.isNotBlank(token)) {
                String tokenUsername = JwtTokenUtils.getUsernameFromToken(token);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    // 校验认证信息中的用户跟令牌中是否一致
                    JwtUserDetails principal = (JwtUserDetails) authentication.getPrincipal();
                    String authUsername = principal.getUsername();
                    if (!authUsername.equals(tokenUsername)) {
                        refreshAuthentication(token, tokenUsername);
                    }
                } else {
                    refreshAuthentication(token, tokenUsername);
                }
            }
        } catch (Exception e) {
            //
        }
    }

    private void refreshAuthentication(String token, String tokenUsername) {
        Set<String> permissions = userService.findPermissions(tokenUsername);
        List<JwtGrantedAuthority> grantedAuthorities = permissions.stream()
                .map(JwtGrantedAuthority::new)
                .collect(Collectors.toList());

        Authentication authentication = new JwtAuthenticationToken(tokenUsername, null, grantedAuthorities, token);
        // 认证成功存储认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}