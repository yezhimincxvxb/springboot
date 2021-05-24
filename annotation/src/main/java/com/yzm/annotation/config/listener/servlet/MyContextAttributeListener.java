package com.yzm.annotation.config.listener.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

/**
 * ServletContextAttributeListener --监听ServletContextAttribute属性的变化
 */
@Slf4j
public class MyContextAttributeListener implements ServletContextAttributeListener {
    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {
        if (scae.getName().equals("context_name")) {
            log.info("Context 添加属性");
            System.out.println(scae.getName() + "：" + scae.getValue());
        }
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent scae) {
        if (scae.getName().equals("context_name")) {
            log.info("Context 移除属性");
            System.out.println(scae.getName() + "：" + scae.getValue());
        }
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {
        if (scae.getName().equals("context_name")) {
            log.info("Context 修改属性");
            System.out.println(scae.getName() + "：" + scae.getValue());
        }
    }
}
