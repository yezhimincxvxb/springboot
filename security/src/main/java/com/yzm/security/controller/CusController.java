package com.yzm.security.controller;

import com.yzm.security.config.cus.LoginFilter;
import com.yzm.security.entity.TokenResult;
import com.yzm.security.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class CusController {

    @GetMapping("/interceptor/login")
    public TokenResult interceptorLogin(
            @RequestParam(required = false, defaultValue = "admin") String username,
            @RequestParam(required = false, defaultValue = "123456") String password) {
        Map<String, Object> map = new HashMap<>();
        map.put(JwtTokenUtils.USERNAME, username);
        map.put(JwtTokenUtils.PASSWORD, password);

        map.put("type", "access");
        String accessToken = JwtTokenUtils.generateToken(map, 120 * 1000L);

        map.put("type", "refresh");
        String refreshToken = JwtTokenUtils.generateToken(map, 300 * 1000L);

        TokenResult token = new TokenResult();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        return token;
    }

    @GetMapping("/filter/login")
    public String filterLogin(
            @RequestParam(required = false, defaultValue = "admin") String username,
            @RequestParam(required = false, defaultValue = "123456") String password) {
        Map<String, Object> map = new HashMap<>();
        map.put(JwtTokenUtils.USERNAME, username);
        map.put(JwtTokenUtils.PASSWORD, password);
        String token = JwtTokenUtils.generateToken(map);
        LoginFilter.TOKEN_MAP.put(username, token);
        return token;
    }

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        log.info("hello！" + request.getAttribute("username"));
        return "hello成功！！！";
    }

}
