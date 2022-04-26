package com.yzm.thread.executor;

import javafx.beans.binding.When;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
public class ExecutorConfig {

    /**
     * 固定大小线程池
     */
    @Bean(name = "fixedThreadPool")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(3, new ThreadFactory() {
            int i = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "fixed-" + i++);
            }
        });
    }

    /**
     * 缓存线程池
     */
    @Bean(name = "cachedThreadPool")
    public ExecutorService cachedThreadPool() {
        return Executors.newCachedThreadPool(new ThreadFactory() {
            int i = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "cached-" + i++);
            }
        });
    }

    /**
     * 单一线程池
     */
    @Bean(name = "singleThreadExecutor")
    public ExecutorService singleThreadExecutor() {
        return Executors.newSingleThreadExecutor(new ThreadFactory() {
            int i = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "single-" + i++);
            }
        });
    }

    /**
     * 周期线程池
     */
    @Bean(name = "scheduledThreadPool")
    public ScheduledExecutorService scheduledThreadPool() {
        return Executors.newScheduledThreadPool(5, new ThreadFactory() {
            int i = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "scheduled-" + i++);
            }
        });
    }

    /**
     * 单一周期线程池
     */
    @Bean(name = "singleThreadScheduledExecutor", destroyMethod = "shutdown")
    public ScheduledExecutorService singleThreadScheduledExecutor() {
        return Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            int i = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "single-sch-" + i++);
            }
        });
    }

    /**
     * 使用LinkedBlockingDeque队列
     */
    @Bean(name = "threadPoolExecutorA", destroyMethod = "shutdown")
    public ThreadPoolExecutor threadPoolExecutorA() {
        return new ThreadPoolExecutor(
                3,
                6,
                10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(5),
                new ThreadFactory() {
                    int i = 0;

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "threadA-" + i++);
                    }
                },
                new ThreadPoolExecutor.DiscardPolicy());
    }

    /**
     * 使用SynchronousQueue队列
     */
    @Bean(name = "threadPoolExecutorB", destroyMethod = "shutdown")
    public ThreadPoolExecutor threadPoolExecutorB() {
        return new ThreadPoolExecutor(
                3,
                6,
                10, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadFactory() {
                    int i = 0;

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "threadB-" + i++);
                    }
                },
                new ThreadPoolExecutor.DiscardPolicy());
    }

    /**
     * 使用ArrayBlockingQueue队列
     */
    @Bean(name = "threadPoolExecutorC", destroyMethod = "shutdown")
    public ThreadPoolExecutor threadPoolExecutorC() {
        return new ThreadPoolExecutor(
                3,
                6,
                10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                new ThreadFactory() {
                    int i = 0;

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "threadC-" + i++);
                    }
                },
                new ThreadPoolExecutor.DiscardPolicy());
    }

    /**
     * 配置：ThreadPoolTaskExecutor
     */
    @Bean(name = "threadPoolTaskExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置线程池前缀：方便排查
        executor.setThreadNamePrefix("thread-task-");
        // 设置线程池的核心线程数量
        executor.setCorePoolSize(10);
        // 设置线程池的最大值
        executor.setMaxPoolSize(15);
        // 设置线程池的队列大小
        executor.setQueueCapacity(250);
        // 设置线程最大空闲时间，单位：秒(非核心线程数)
        executor.setKeepAliveSeconds(3000);
        // 饱和策略
        // AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常
        // CallerRunsPolicy:若已达到待处理队列长度，将由主线程直接处理请求
        // DiscardOldestPolicy:抛弃旧的任务；会导致被丢弃的任务无法再次被执行
        // DiscardPolicy:抛弃当前任务；会导致被丢弃的任务无法再次被执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        // 实现了InitializingBean接口，可不需要执行这一初始化步骤
        // executor.initialize();
        return executor;
    }

    /**
     * 配置：ScheduledThreadPoolExecutor
     */
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