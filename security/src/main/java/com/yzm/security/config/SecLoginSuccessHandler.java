package com.yzm.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后进行业务处理
 */
@Slf4j
public class SecLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("登录成功");
        // 生成并返回token给客户端，后续访问携带此token
        // HttpUtils.successWrite(response, JwtTokenUtils.generateToken(authentication));
        response.sendRedirect(request.getContextPath() + "/home");
    }

}