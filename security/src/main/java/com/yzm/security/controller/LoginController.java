package com.yzm.security.controller;

import com.yzm.security.entity.JwtAuthenticationToken;
import com.yzm.security.entity.LoginUser;
import com.yzm.security.utils.HttpResult;
import com.yzm.security.utils.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 登录控制器
 */
@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    public HttpResult login(@RequestBody LoginUser loginBean, HttpServletRequest request) throws IOException {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();

        // 系统登录认证
        JwtAuthenticationToken authToken = new JwtAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 执行登录认证过程
        Authentication authentication = authenticationManager.authenticate(authToken);
        // 认证成功存储认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌并返回给客户端
        authToken.setToken(JwtTokenUtils.generateToken(authentication));
        return HttpResult.ok(authToken);
    }

    @GetMapping("/cors")
    public String cors() {
        return "success";
    }

    //登录成功后重定向地址
    @RequestMapping("/loginSuccess")
    public String loginSuccess() {
        return "登录成功";
    }

}