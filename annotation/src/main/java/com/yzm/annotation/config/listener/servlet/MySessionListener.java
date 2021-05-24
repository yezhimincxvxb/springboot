package com.yzm.annotation.config.listener.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * HttpSessionListener -- 监听HttpSession对象的创建以及销毁
 * request.getSession时创建，通过设置过期时间销毁session.setMaxInactiveInterval(10);单位：秒(不设置也有默认过期时间)
 */
@Slf4j
public class MySessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("session 创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("session 销毁");
    }
}
