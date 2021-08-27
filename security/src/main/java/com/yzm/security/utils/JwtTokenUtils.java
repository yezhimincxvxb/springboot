package com.yzm.security.utils;

import com.yzm.security.jwt.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

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
     * token前缀
     */
    public static final String TOKEN_PREFIX  = "Basic ";
    public static final String TOKEN_HEADER = "Authorization";
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
        Map<String, Object> claims = new HashMap<>();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
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
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // token过期是直接抛出异常的，但仍然可以获取到claims对象
            claims = e.getClaims();
        }

        if (new Date().before(claims.getExpiration()))
            return claims;
        return null;
    }


    /**
     * 从令牌中获取用户名
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = verifyToken(token);
        return claims == null ? null : claims.getSubject();
    }

    /**
     * 获取请求token
     */
    public static String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        if (token == null) token = request.getHeader("token");
        if (StringUtils.isBlank(token)) return null;

        if (token.startsWith(TOKEN_PREFIX)) {
            token = token.substring(TOKEN_PREFIX.length());
        }
        return token;
    }

}
