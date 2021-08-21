package com.yzm.security.config.token;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

/**
 * 将自定义过滤器注册到 spring 过滤链中
 */
//@Configuration
public class FilterConfig {

    /**
     * 配置过滤器
     */
    @Bean
    FilterRegistrationBean<Filter> filterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new LoginFilter());
        registration.setName("loginFilter");
        //优先级，值越小级别越高
        registration.setOrder(-1);
        //拦截/*的访问 多级匹配（springboot 过滤器Filter/*以及匹配 /**多级匹配）
        registration.addUrlPatterns("/*");
        registration.addInitParameter("excludedUris","/filter/index,/filter/login");
        return registration;
    }

}
