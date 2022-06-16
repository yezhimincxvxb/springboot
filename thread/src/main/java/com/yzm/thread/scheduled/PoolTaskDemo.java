package com.yzm.thread.scheduled;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.*;

@Slf4j
//@Component
public class PoolTaskDemo {

    @Resource(name = "scheduledPoolTaskExecutor")
    private ScheduledThreadPoolExecutor scheduled;

    @PostConstruct
    public void scheduledPoolTaskExecutor() {
//        schedule();
//        scheduleAtFixedRate();
        scheduleWithFixedDelay();
    }


    public void schedule() {
        log.info("调用前");
        scheduled.schedule(new Runnable() {
            @Override
            public void run() {
                log.info("方法调用，延迟5秒执行一次, 只执行一次");
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    public void scheduleAtFixedRate() {
        log.info("调用前");
        scheduled.scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.info("方法调用，延迟5秒首次执行，之后每过3秒轮询, 不受run方法执行耗时影响");
                Thread.sleep(2000);
            }
        }, 5000, 3000, TimeUnit.MILLISECONDS);
    }

    public void scheduleWithFixedDelay() {
        log.info("调用前");
        scheduled.scheduleWithFixedDelay(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.info("方法调用，延迟5秒首次执行，之后每过3秒轮询，受run方法执行耗时影响");
                Thread.sleep(2000);
            }
        }, 5000, 3000, TimeUnit.MILLISECONDS);
    }

}
