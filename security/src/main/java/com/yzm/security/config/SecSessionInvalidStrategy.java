package com.yzm.security.config;

import com.yzm.security.constant.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Session 无效
 */
@Slf4j
public class SecSessionInvalidStrategy implements InvalidSessionStrategy {

    private boolean createNewSession = true;

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Session 过期了");

        if (this.createNewSession) {
            request.getSession();
        }

        // 清除cookie
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("JSESSIONID")) {
//                cookie.setMaxAge(0);
//                cookie.setPath("/");
//                cookie.setSecure(request.isSecure());
//                response.addCookie(cookie);
//            }
//        }

        response.sendRedirect(request.getContextPath() + SysConstant.LOGIN_PAGE + "?timeout");
    }

    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}
