package com.yzm.annotation.config.listener.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * HttpSessionAttributeListener  --监听HttpSessionAttribute属性的变化
 */
@Slf4j
public class MySessionAttributeListener implements HttpSessionAttributeListener {
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        if (se.getName().equals("session_name")) {
            log.info("Session 添加属性");
            System.out.println(se.getName() + "：" + se.getValue());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        if (se.getName().equals("session_name")) {
            log.info("Session 移除属性");
            System.out.println(se.getName() + "：" + se.getValue());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        if (se.getName().equals("session_name")) {
            log.info("Session 修改属性");
            System.out.println(se.getName() + "：" + se.getValue());
        }
    }
}
