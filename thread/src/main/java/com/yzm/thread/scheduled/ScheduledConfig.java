package com.yzm.thread.scheduled;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableScheduling
public class ScheduledConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(scheduledThreadPoolExecutor());
    }

    @Bean(name = "scheduledPoolTaskExecutor", destroyMethod = "shutdown")
    public ScheduledThreadPoolExecutor scheduledThreadPoolExecutor() {
        // 第一个参数：核心线程数
        // 第二个参数：线程工厂(此处用来设置线程名)
        // 第三次参数：饱和策略
        // 饱和策略
        // AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常
        // CallerRunsPolicy:若已达到待处理队列长度，将由主线程直接处理请求
        // DiscardOldestPolicy:抛弃旧的任务；会导致被丢弃的任务无法再次被执行
        // DiscardPolicy:抛弃当前任务；会导致被丢弃的任务无法再次被执行
        return new ScheduledThreadPoolExecutor(10,
                new ThreadFactory() {
                    int i = 0;

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "scheduled-" + i++);
                    }
                },
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }

}
