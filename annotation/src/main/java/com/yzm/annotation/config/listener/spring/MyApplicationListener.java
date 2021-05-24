package com.yzm.annotation.config.listener.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableAsync
public class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {

    @Async
    @Override
    public void onApplicationEvent(MyApplicationEvent event) {
        log.info("监听：" + event.getTask().getName() + "=" + event.getTask().getWork());
    }
}
