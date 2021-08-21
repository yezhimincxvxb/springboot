package com.yzm.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtils {

    public static final long TOKEN_EXPIRED_TIME = 180 * 1000L;
    public static final long TOKEN_REFRESH_TIME = 100 * 1000L;

    /**
     * jwt 加密解密密钥(可自行填写)
     */
    private static final String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";

    /**
     * 生成令牌
     */
    public static String generateToken(Map<String, Object> claims) {
        return generateToken(claims, 0L);
    }

    public static String generateToken(Map<String, Object> claims, Long expiredTime) {
        if (expiredTime < TOKEN_EXPIRED_TIME) expiredTime = TOKEN_EXPIRED_TIME;

        //下面就是在为payload添加各种标准声明和私有声明了
        return Jwts.builder()
                //.setHeader()
                //JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击
                .setId(UUID.randomUUID().toString())
                //.setIssuer("该JWT的签发者，是否使用是可选的")
                //.setSubject("该JWT所面向的用户，是否使用是可选的")
                //.setAudience("接收该JWT的一方，是否使用是可选的")
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //签发时间
                .setIssuedAt(new Date())
                //在指定时间之前令牌是无效的
                .setNotBefore(new Date())
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(SignatureAlgorithm.HS256, generalKey())
                .compact();
    }

    /**
     * 验证令牌
     */
    public static Claims verifyToken(String token) {
        return Jwts.parser()
                //签名秘钥
                .setSigningKey(generalKey())
                .parseClaimsJws(token).getBody();
    }

    /**
     * 由字符串生成加密key
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decodeBase64(JWT_SECRET);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public static void main(String[] args) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", "admin");
        claims.put("age", 21);

        //生成token
        String token = JwtUtils.generateToken(claims);
        System.out.println(token);

        //解析
        Claims verify = JwtUtils.verifyToken(token);
        System.out.println(verify.getId());
        System.out.println(verify.get("name", String.class));
        System.out.println(verify.get("age", Integer.class));
        System.out.println(verify.getSubject());
        System.out.println(verify.getIssuedAt());
        System.out.println(verify.getExpiration());
    }

}
