package com.yzm.annotation.config.listener.servlet;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.EventListener;

//@Configuration
public class ListenerConfig {

    @Bean
    public ServletListenerRegistrationBean<EventListener> addListener() {
        ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new MyContextListener());
        registrationBean.setListener(new MyContextAttributeListener());
        registrationBean.setListener(new MySessionListener());
        registrationBean.setListener(new MySessionAttributeListener());
        registrationBean.setListener(new MyRequestListener());
        registrationBean.setListener(new MyRequestAttributeListener());
        return registrationBean;
    }

}
