package com.yzm.security.jwt;

import com.yzm.security.service.UserService;
import com.yzm.security.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限验证过滤器
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    private final AuthenticationManager authenticationManager;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = JwtTokenUtils.getTokenFromRequest(request);
            if (StringUtils.isNotBlank(token)) {
                Claims claims = JwtTokenUtils.verifyToken(token);
                if (claims != null) {
                    String tokenUsername = claims.getSubject();
                    if (authenticationIsRequired(tokenUsername)) {
                        Collection<GrantedAuthority> grantedAuthorities = (Collection<GrantedAuthority>) claims.get(JwtTokenUtils.AUTHORITIES);
                        if (grantedAuthorities == null) {
                            Set<String> permissions = userService.findPermissions(tokenUsername);
                            grantedAuthorities = permissions.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());
                        }

                        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(tokenUsername, null, grantedAuthorities);
                        authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        Authentication authenticate = this.authenticationManager.authenticate(authRequest);

                        // 认证成功存储认证信息到上下文
                        SecurityContextHolder.getContext().setAuthentication(authenticate);
                    }
                }
            } else {
                log.info("token or authorization not included in request header");
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean authenticationIsRequired(String username) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth != null && existingAuth.isAuthenticated()) {
            return existingAuth instanceof UsernamePasswordAuthenticationToken && !existingAuth.getName().equals(username) || existingAuth instanceof AnonymousAuthenticationToken;
        } else {
            return true;
        }
    }

}