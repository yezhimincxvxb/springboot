package com.yzm.annotation.config.listener.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

/**
 * ServletRequestAttributeListener  --监听request对象中属性的改变
 */
@Slf4j
public class MyRequestAttributeListener implements ServletRequestAttributeListener {
    @Override
    public void attributeAdded(ServletRequestAttributeEvent srae) {
        // 监听指定的属性名
        if (srae.getName().equals("request_name")) {
            log.info("Request 添加参数");
            System.out.println(srae.getName() + "：" + srae.getValue());
        }
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent srae) {
        if (srae.getName().equals("request_name")) {
            log.info("Request 移除参数");
            System.out.println(srae.getName() + "：" + srae.getValue());
        }
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent srae) {
        if (srae.getName().equals("request_name")) {
            log.info("Request 修改参数");
            System.out.println(srae.getName() + "：" + srae.getValue());
        }
    }
}
