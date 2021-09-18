package com.yzm.security.config;

import com.yzm.security.constant.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败后进行业务处理
 */
@Slf4j
public class SecLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("登录失败");
        //HttpUtils.errorWrite(response,"login failure");
        response.sendRedirect(request.getContextPath() + SysConstant.LOGIN_PAGE + "?error");
    }
}
