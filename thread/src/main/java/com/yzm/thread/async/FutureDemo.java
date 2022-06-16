package com.yzm.thread.async;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.function.Supplier;

@Slf4j
public class FutureDemo {

    public static void main(String[] args) throws Exception {
//        demo01();
//        demo02();
//        demo03();
//        demo04();
//        demo05();
        demo06();
    }

    private static final ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactory() {
        int i = 0;

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "test-" + i++);
        }
    });

    public static void demo01() {
        log.info("创建异步任务");
        Future<String> submit = executor.submit(new Callable<String>() {
            @Override
            public String call() {
                String result = "fail";
                try {
                    log.info("开始执行异步任务");
                    // 执行任务耗时
                    Thread.sleep(10000);
                    result = "success";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return result;
            }
        });


        try {
            String result = submit.get();
            log.info("获取异步任务结果 " + result);
        } catch (InterruptedException e) {
            System.out.println("中断异常");
        } catch (ExecutionException e) {
            System.out.println("执行异常");
        }

        log.info("Future的get方法，会使当前线程阻塞");
    }

    public static void demo02() throws InterruptedException, ExecutionException {
        log.info("创建异步任务");
        Future<String> submit = executor.submit(new Callable<String>() {
            @Override
            public String call() {
                String result = "fail";
                try {
                    log.info("开始执行异步任务");
                    // 执行任务耗时
                    Thread.sleep(10000);
                    result = "success";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return result;
            }
        });

        log.info("轮询调用isDone方法查询异步任务是否完成");
        while (true) {
            if (submit.isDone()) {
                String result = submit.get();
                log.info(result);
                break;
            } else {
                log.info("异步任务还未完成，先干点别的事");
                Thread.sleep(1000);
            }
        }

        log.info("Future的get方法，会使当前线程阻塞");
    }

    private static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5, new ThreadFactory() {
        int i = 0;

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "fixed-" + i++);
        }
    });

    public static void demo03() throws ExecutionException, InterruptedException {
        log.info("创建异步任务");
        CompletableFuture<String> finalResult = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                log.info("执行异步任务");
                try {
                    Thread.sleep((long) (Math.random() * 5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //有异常
                //if (true) throw new RuntimeException("异常");
                return "success";
            }
        }, fixedThreadPool).exceptionally((exception) -> {
            try {
                log.info("异常处理 " + exception);
                Thread.sleep((long) (Math.random() * 5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "exception";
        }).thenApplyAsync((result) -> {
            log.info("上层任务结果： " + result);
            try {
                Thread.sleep((long) (Math.random() * 5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "over";
        }, fixedThreadPool);

        //异常通过future的get方法抛出，如果注释掉get的方法就不会打印异常信息
        log.info("最终结果 = " + finalResult.get());
    }

    public static void demo04() throws ExecutionException, InterruptedException {
        log.info("创建异步任务");
        CompletableFuture<String> finalResult = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                log.info("执行异步任务");
                try {
                    Thread.sleep((long) (Math.random() * 5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //有异常
                if (true) throw new RuntimeException("异常");
                return "success";
            }
        }, fixedThreadPool).handleAsync((result, exception) -> {
            if (exception == null) {
                log.info("上层任务无异常，获取到上层结果为：" + result);
            } else {
                log.info("上层任务有异常，获取到上层结果为：" + result, exception);
            }
            return "handle " + result;
        }, fixedThreadPool);

        //异常通过future的get方法抛出，如果注释掉get的方法就不会打印异常信息
        log.info("最终结果 = " + finalResult.get());
    }

    public static void demo05() throws ExecutionException, InterruptedException {
        log.info("创建异步任务");
        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int i = 0;
                try {
                    log.info("开始执行异步任务");
                    Thread.sleep((long) (Math.random() * 5000));
                    i = 1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return i;
            }
        }, fixedThreadPool);

        CompletableFuture<Integer> supplyAsync2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int i = 0;
                try {
                    log.info("开始执行异步任务");
                    Thread.sleep((long) (Math.random() * 8000));
                    i = 2;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //有异常
                if (true) throw new RuntimeException("异常");
                return i;
            }
        }, fixedThreadPool);

        CompletableFuture<Integer> thenCombineAsync = supplyAsync.thenCombineAsync(supplyAsync2, (a, b) -> {
            log.info("a = " + a + ", b = " + b);
            return a + b;
        }, fixedThreadPool);
        log.info("thenCombineAsync = " + thenCombineAsync.get());

    }

    public static void demo06() throws ExecutionException, InterruptedException {
        log.info("创建异步任务");
        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int i = 0;
                try {
                    log.info("执行异步任务");
                    Thread.sleep((long) (Math.random() * 5000));
                    i = 1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return i;
            }
        }, fixedThreadPool);

        CompletableFuture<Integer> supplyAsync2 = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                int i = 0;
                try {
                    log.info("执行异步任务");
                    Thread.sleep((long) (Math.random() * 5000));
                    i = 2;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return i;
            }
        }, fixedThreadPool);

        CompletableFuture<Integer> thenCombineAsync = supplyAsync.applyToEitherAsync(supplyAsync2, (result) -> {
            log.info("result " + result);
            return 3;
        }, fixedThreadPool);

        log.info("final result = " + thenCombineAsync.get());
    }

}
