package com.yzm.ehcache.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching // 开启缓存
public class EhcacheConfig /*implements KeyGenerator*/ {

    /**
     * Ehcache：Key生成器
     */
    /*@Override
    public Object generate(Object o, Method method, Object... objects) {
        return null;
    }*/
}
