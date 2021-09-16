package com.yzm.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 添加视图控制器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login/invalid").setViewName("invalid");
        registry.addViewController("/user/login").setViewName("login");
    }

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")    // 允许跨域访问的路径
                .allowedOriginPatterns("*")    // 允许跨域访问的源
                .allowedMethods("*")    // 允许请求方法
                .allowedHeaders("*")  // 允许头部设置
                .maxAge(168000)    // 预检间隔时间
                .allowCredentials(true);    // 是否发送cookie
    }
}