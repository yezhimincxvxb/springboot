package com.yzm.annotation.config.listener.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ServletContextListener -- 监听servletContext对象的创建以及销毁
 * 程序启动时创建，程序关闭时销毁。只创建一次
 */
@Slf4j
public class MyContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("ServletContext 创建");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("ServletContext 销毁");
    }
}
