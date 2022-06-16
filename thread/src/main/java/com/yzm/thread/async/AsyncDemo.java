package com.yzm.thread.async;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class AsyncDemo {

    private final AsyncService asyncService;

    public AsyncDemo(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @PostConstruct
    public void demo() {
        asyncA();
//        asyncB(1);
//        asyncC(11);
//        asyncD();
    }

    public void asyncA() {
        asyncService.async();
    }

    public void asyncB(int i) {
        asyncService.async(i);
    }

    public void asyncC(int i) {
        try {
            Future<String> future = asyncService.asyncReturn(i);
            // 这里使用了循环判断，等待获取结果信息
            while (true) {
                // 判断是否执行完毕
                if (future.isDone()) {
                    System.out.println("执行完毕，结果为：" + future.get());
                    break;
                }
                System.out.println("还未执行完毕，请稍等。。。");
                Thread.sleep(1000);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("异步调用失败");
            e.printStackTrace();
        }
    }

    public void asyncD() {
        asyncService.D();
    }
}
