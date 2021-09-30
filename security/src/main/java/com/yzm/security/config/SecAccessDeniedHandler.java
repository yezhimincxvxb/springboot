package com.yzm.security.config;

import com.yzm.security.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权异常处理类
 */
@Slf4j
public class SecAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("授权失败：" + accessDeniedException.getMessage());
        response.setStatus(403);
        HttpUtils.errorWrite(response, "Forbidden:" + accessDeniedException.getMessage());
    }
}
