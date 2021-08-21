package com.yzm.security.utils;

import com.yzm.security.entity.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWT工具类
 */
public class JwtTokenUtils implements Serializable {

    private static final long serialVersionUID = 8527289053988618229L;
    /**
     * 用户名称
     */
    public static final String USERNAME = Claims.SUBJECT;
    /**
     * 权限列表
     */
    public static final String AUTHORITIES = "authorities";
    /**
     * 密钥
     */
    private static final String SECRET = "abcdefgh";
    /**
     * 有效期12小时
     */
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    /**
     * 生成令牌
     */
    public static String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>(3);
        JwtUserDetails principal = (JwtUserDetails) authentication.getPrincipal();
        claims.put(USERNAME, principal.getUsername());
        claims.put(AUTHORITIES, authentication.getAuthorities());
        return generateToken(claims);
    }

    private static String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setClaims(claims)
                .setIssuedAt(new Date()) // 签发时间
                .setNotBefore(new Date())  // 生效时间
                .setExpiration(expirationDate) // 过期时间
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 验证令牌
     */
    public static Claims verifyToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            //
        }
        return claims;
    }

    /**
     * 从令牌中获取用户名
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = verifyToken(token);
        if (claims == null) return null;
        return claims.getSubject();
    }

    /**
     * 获取请求token
     */
    public static String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String prefix = "Bearer ";
        if (token == null) {
            token = request.getHeader("token");
        } else if (token.startsWith(prefix)) {
            token = token.substring(prefix.length());
        }
        return token;
    }

}
