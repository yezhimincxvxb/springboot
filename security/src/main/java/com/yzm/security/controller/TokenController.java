//package com.yzm.security.controller;
//
//import com.yzm.security.entity.TokenResult;
//import com.yzm.security.utils.JwtUtils;
//import io.swagger.annotations.Api;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@RestController
//@Api(value = "token", tags = {"token"})
//public class TokenController {
//
//    @GetMapping("/interceptor/login")
//    public TokenResult interceptorLogin(Integer userid, String username, String password) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("userid", userid);
//        map.put("username", username);
//        map.put("password", password);
//        String accessToken = JwtUtils.generateToken(map, 180 * 1000L);
//        String refreshToken = JwtUtils.generateToken(map, 300 * 1000L);
//        TokenResult token = new TokenResult();
//        token.setAccessToken(accessToken);
//        token.setRefreshToken(refreshToken);
//        return token;
//    }
//
//    @GetMapping("/filter/login")
//    public String filterLogin(Integer userid, String username, String password) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("userid", userid);
//        map.put("username", username);
//        map.put("password", password);
//        return JwtUtils.generateToken(map);
//    }
//
//    @GetMapping("/hello")
//    public String hello(HttpServletRequest request) {
//        log.info("" + request.getAttribute("userid"));
//        return "请求成功！！！";
//    }
//
//}
