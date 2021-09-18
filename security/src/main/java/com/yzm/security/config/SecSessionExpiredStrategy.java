package com.yzm.security.config;

import com.yzm.security.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

/**
 * Session 过期策略
 */
@Slf4j
public class SecSessionExpiredStrategy implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        log.info("被迫下线");
        HttpUtils.errorWrite(event.getResponse(), "已经另一台机器登录，您被迫下线。" + event.getSessionInformation().getLastRequest());

        // 如果是跳转html页面，url代表跳转的地址
        // redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), "url");
    }

}
