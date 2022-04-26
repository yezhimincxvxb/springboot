package com.yzm.thread.executor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;

@Slf4j
//@Component
@EnableScheduling
public class ExecutorTestService {

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void fixedRate() throws Exception {
//        threadPoolTaskExecutor();
        scheduledPoolTaskExecutor();
    }

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    public void threadPoolTaskExecutor() {
        for (int i = 0; i < 20; i++) {
            taskExecutor.execute(() -> {
                try {
                    Thread.sleep(2000);
                    log.info("ThreadPoolTaskExecutor 的 execute 方法调用");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Resource(name = "scheduledPoolTaskExecutor")
    private ScheduledThreadPoolExecutor scheduled;

    public void scheduledPoolTaskExecutor() throws Exception {
//        scheduleA();
//        scheduleB();
//        scheduleC();
        scheduleD();
    }

    public void scheduleA() throws ExecutionException, InterruptedException {
        scheduled.execute(new Runnable() {
            @Override
            public void run() {
                log.info("execute ");
            }
        });

        Thread.sleep(2000);
        scheduled.submit(new Runnable() {
            @Override
            public void run() {
                log.info("submit 无返回值");
            }
        });

        Thread.sleep(2000);
        Future<String> result = scheduled.submit(new Runnable() {
            @Override
            public void run() {
                log.info("submit 有返回值");
            }
        }, "ok");
        System.out.println("result = " + result.get());

        Thread.sleep(2000);
        Future<String> submit = scheduled.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("submit 有返回值");
                return "ok";
            }
        });
        System.out.println("submit = " + submit.get());
    }

    public void scheduleB() throws ExecutionException, InterruptedException {
        scheduled.schedule(new Runnable() {
            @Override
            public void run() {
                log.info("方法调用，延迟5秒执行一次, 只执行一次");
            }
        }, 5000, TimeUnit.MILLISECONDS);

        ScheduledFuture<String> result = scheduled.schedule(new Callable<String>() {
            @Override
            public String call() {
                log.info("方法调用，延迟10秒执行一次, 只执行一次");
                return "ok";
            }
        }, 10000, TimeUnit.MILLISECONDS);
        System.out.println("result = " + result.get());
    }

    public void scheduleC() {
        scheduled.scheduleAtFixedRate(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.info("方法调用，延迟5秒首次执行，之后每过3秒轮询, 不受run方法执行耗时影响");
                Thread.sleep(2000);
            }
        }, 5000, 3000, TimeUnit.MILLISECONDS);
    }

    public void scheduleD() {
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
