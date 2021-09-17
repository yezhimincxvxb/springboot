package com.yzm.jwt.controllrt;

import com.yzm.jwt.config.LoginFilter;
import com.yzm.jwt.config.LoginInterceptor;
import com.yzm.jwt.entity.TokenResult;
import com.yzm.jwt.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "welcome to home";
    }

    @GetMapping("/inter/login")
    public TokenResult intLogin(
            @RequestParam(required = false, defaultValue = "admin") String username,
            @RequestParam(required = false, defaultValue = "123456") String password) {
        Map<String, Object> map = new HashMap<>();
        map.put(JwtUtils.USERNAME, username);
        map.put(JwtUtils.PASSWORD, password);

        // 一次性获取访问token、刷新token
        map.put("type", LoginInterceptor.ACCESS);
        String accessToken = JwtUtils.generateToken(map, 120 * 1000L);
        map.put("type", LoginInterceptor.REFRESH);
        String refreshToken = JwtUtils.generateToken(map, 300 * 1000L);

        LoginInterceptor.TOKEN_MAP.put(username + "_" + LoginInterceptor.ACCESS, accessToken);
        LoginInterceptor.TOKEN_MAP.put(username + "_" + LoginInterceptor.REFRESH, refreshToken);
        return TokenResult.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @GetMapping("/filter/login")
    public String filLogin(
            @RequestParam(required = false, defaultValue = "admin") String username,
            @RequestParam(required = false, defaultValue = "123456") String password) {
        Map<String, Object> map = new HashMap<>();
        map.put(JwtUtils.USERNAME, username);
        map.put(JwtUtils.PASSWORD, password);
        String token = JwtUtils.generateToken(map);

        LoginFilter.TOKEN_MAP.put(username, token);
        return token;
    }

    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        Object username = request.getAttribute("username");
        log.info("hello！" + username);
        return "hello " + username + " ！！！";
    }

}
