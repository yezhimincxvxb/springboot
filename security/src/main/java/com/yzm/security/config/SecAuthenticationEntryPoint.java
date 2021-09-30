package com.yzm.security.config;

import com.yzm.security.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常处理类
 * Security默认是跳转到登录页的
 */
@Slf4j
public class SecAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("认证失败：" + authException.getMessage());
        HttpUtils.errorWrite(response, "login failed:" + authException.getMessage());
    }
}
