package com.yzm.annotation.config.listener.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * ServletRequestListener -- 监听request对象的创建以及销毁
 * 请求时创建，响应后销毁。每次请求都会创建
 */
@Slf4j
public class MyRequestListener implements ServletRequestListener {
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        log.info("ServletRequest 创建");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        log.info("ServletRequest 销毁");
    }
}
