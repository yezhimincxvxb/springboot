package com.yzm.security.config.sec;

import com.yzm.security.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权限验证过滤器
 */
@Slf4j
public class SecAuthorizationFilter extends BasicAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public SecAuthorizationFilter(AuthenticationManager authenticationManager) {
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
                    String username = claims.getSubject();
                    if (authenticationIsRequired(username)) {

                        Object authors = claims.get(JwtTokenUtils.AUTHORITIES);
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        if (authors instanceof List) {
                            for (Object object : (List) authors) {
                                authorities.add(new SimpleGrantedAuthority((String) ((Map) object).get("authority")));
                            }
                        }

                        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, null, authorities);
                        authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        Authentication authenticate = this.authenticationManager.authenticate(authRequest);

                        // 认证成功存储认证信息到上下文
                        SecurityContextHolder.getContext().setAuthentication(authenticate);
                    }
                }
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            log.info("Failed to process authentication request");
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