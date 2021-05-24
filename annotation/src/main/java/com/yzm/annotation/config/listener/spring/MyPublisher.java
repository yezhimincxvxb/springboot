package com.yzm.annotation.config.listener.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 事件发布
 */
@Slf4j
@Component
public class MyPublisher {

    @Autowired
    private ApplicationContext applicationContext;

    public void publish(Task task) {
        log.info("事件发布");
        applicationContext.publishEvent(new MyApplicationEvent(this, task));
    }

}
