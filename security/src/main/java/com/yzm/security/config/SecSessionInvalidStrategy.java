package com.yzm.security.config;

import com.yzm.security.constant.SysConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SecSessionInvalidStrategy implements InvalidSessionStrategy {

    private boolean createNewSession = true;

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Session 过期了");

        if (this.createNewSession) {
            request.getSession();
        }

        response.sendRedirect(request.getContextPath() + "/invalid");
    }

    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}
