package com.yzm.thread.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Slf4j
@Component
public class AsyncService {

    /**
     * 1.无参无返回值方法
     * 最简单的异步调用，返回值为void
     */
    @Async
    public void async() {
        log.info("无参无返回值方法，通过观察线程名称以便查看效果");
        int a = 1 / 0;
    }

    /**
     * 2.有参无返回值方法
     * 指定线程池
     *
     * @param i 传入参数
     */
    @Async("another_async_pool")
    public void async(int i) {
        log.info("有参无返回值方法, 参数={}", i);
    }

    /**
     * 3.有参有返回值方法
     *
     * @param i 传入参数
     * @return Future
     */
    @Async
    public Future<String> asyncReturn(int i) throws InterruptedException {
        log.info("有参有返回值方法, 参数={}", i);
//        int a = 1 / 0;
        Thread.sleep(100);
        return new AsyncResult<String>("success:" + i);
    }

    /**
     * @Async  必须不同类间调用：
     */
    public void D() {
        log.info("在同类下调用 @Async 方法是同步执行的");
        async();
    }
}
