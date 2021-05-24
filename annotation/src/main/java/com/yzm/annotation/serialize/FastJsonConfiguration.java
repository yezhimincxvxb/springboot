package com.yzm.annotation.serialize;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 注册自定义消息转换器
 */
@Configuration
public class FastJsonConfiguration {

    @Bean
    public HttpMessageConverters customConverters() {
        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        // 创建自定义消息转换器并加入Collection
        CustomHttpMessageConverter jsonFilterHttpMessageConverter = new CustomHttpMessageConverter();
        messageConverters.add(jsonFilterHttpMessageConverter);
        return new HttpMessageConverters(true, messageConverters);
    }

}
