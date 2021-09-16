package com.yzm.security.config.sec1;

import com.yzm.security.utils.HttpUtils;
import com.yzm.security.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SecLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("登录成功");
        // 生成并返回token给客户端，后续访问携带此token
        // HttpUtils.successWrite(response, JwtTokenUtils.generateToken(authentication));
        response.sendRedirect(request.getContextPath() + "/home");
    }

}