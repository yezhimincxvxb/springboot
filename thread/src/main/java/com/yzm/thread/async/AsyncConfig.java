package com.yzm.thread.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 描述：
 */
@Configuration
@EnableAsync // 开启异步调用功能，即使@Async注解生效
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

    @Bean(name = "default_async_pool", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor defaultAsyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置线程池前缀：方便排查
        executor.setThreadNamePrefix("default-async-");
        // 设置线程池的大小
        executor.setCorePoolSize(10);
        // 设置线程池的最大值
        executor.setMaxPoolSize(15);
        // 设置线程池的队列大小
        executor.setQueueCapacity(250);
        // 设置线程最大空闲时间，单位：秒
        executor.setKeepAliveSeconds(3000);
        // 饱和策略
        // AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常
        // CallerRunsPolicy:若已达到待处理队列长度，将由主线程直接处理请求
        // DiscardOldestPolicy:抛弃旧的任务；会导致被丢弃的任务无法再次被执行
        // DiscardPolicy:抛弃当前任务；会导致被丢弃的任务无法再次被执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        return executor;
    }

    @Bean(name = "another_async_pool", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor anotherAsyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("another-task-");
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(6);
        executor.setQueueCapacity(5);
        executor.setKeepAliveSeconds(10);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        return executor;
    }

    /**
     * 自定义异步线程池，若不重写，则使用默认的
     */
    @Override
    public Executor getAsyncExecutor() {
        return defaultAsyncPool();
    }

    /**
     * 1.无参无返回值方法
     * 2.有参无返回值方法
     * 返回值为void的， 通过IllegalArgumentException异常, AsyncUncaughtExceptionHandler 处理异常
     * 3.有参有返回值方法
     * 返回值是Future，不会被AsyncUncaughtExceptionHandler处理，需要我们在方法中捕获异常并处理
     * 或者在调用方在调用Future.get时捕获异常进行处理
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        System.out.println("正在处理无返回值的@Async异步调用方法");
        return (throwable, method, objects) -> {
            log.info("Exception message - " + throwable.getMessage());
            log.info("Method name - " + method.getName());
            for (Object param : objects) {
                log.info("Parameter value - " + param);
            }
        };
    }

}
