package com.yzm.thread.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ExecutorDemo {

    public static void main(String[] args) throws Exception {
//        fixedThreadPool();
//        singleThreadExecutor();
//        cachedThreadPool();
//        scheduledThreadPool();
//        singleThreadScheduledExecutor();
        WorkQueue();
    }

    public static void fixedThreadPool() {
        // 创建固定大小的线程池，线程数3
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3, new ThreadFactory() {
            int i = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "fixed-" + i++);
            }
        });

        for (int i = 0; i < 10; i++) {
            fixedThreadPool.execute(() -> {
                try {
                    Thread.sleep(100);
                    log.info("固定线程池");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    //===============================================================================================

    public static void singleThreadExecutor() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() {
            int i = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "single-" + i++);
            }
        });

        for (int i = 0; i < 10; i++) {
            singleThreadExecutor.execute(() -> {
                try {
                    Thread.sleep(100);
                    log.info("单一线程池");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    //===============================================================================================

    public static void cachedThreadPool() throws InterruptedException {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool(new ThreadFactory() {
            int i = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "cached-" + i++);
            }
        });

        for (int i = 0; i < 10; i++) {
            cachedThreadPool.execute(() -> {
                try {
                    Thread.sleep(100);
                    log.info("缓存线程池");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(5000);

        for (int i = 0; i < 20; i++) {
            cachedThreadPool.execute(() -> {
                try {
                    Thread.sleep(100);
                    log.info("缓存线程池");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    //===============================================================================================

    public static void scheduledThreadPool() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5, new ThreadFactory() {
            int i = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "scheduled-" + i++);
            }
        });

        log.info("周期线程池，调用前");
//        scheduledThreadPoolA(scheduledThreadPool);
//        scheduledThreadPoolB(scheduledThreadPool);
        scheduledThreadPoolC(scheduledThreadPool);
    }

    private static void scheduledThreadPoolA(ScheduledExecutorService scheduledThreadPool) {
        for (int i = 0; i < 10; i++) {
            scheduledThreadPool.schedule(() -> {
                try {
                    Thread.sleep(100);
                    log.info("周期线程池，调用时延迟5秒执行一次任务，只执行一次");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, 5, TimeUnit.SECONDS);
        }
    }

    private static void scheduledThreadPoolB(ScheduledExecutorService scheduledThreadPool) {
        for (int i = 0; i < 10; i++) {
            scheduledThreadPool.scheduleAtFixedRate(() -> {
                try {
                    Thread.sleep(1000);
                    log.info("周期线程池，调用时延迟5秒执行一次任务，之后立即计算每过10秒再次执行一次，会不停重复执行，不受任务耗时影响，时间间隔固定");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, 5, 10, TimeUnit.SECONDS);
        }
    }

    private static void scheduledThreadPoolC(ScheduledExecutorService scheduledThreadPool) {
        for (int i = 0; i < 10; i++) {
            scheduledThreadPool.scheduleWithFixedDelay(() -> {
                try {
                    Thread.sleep(1000);
                    log.info("周期线程池，调用时延迟5秒执行一次任务，任务执行完毕之后才开始计算第10秒再次执行一次，会不停重复执行，受任务耗时影响，时间间隔不固定");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, 5, 10, TimeUnit.SECONDS);
        }
    }

    //===============================================================================================

    public static void singleThreadScheduledExecutor() {
        ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            int i = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "single-sch-" + i++);
            }
        });

        log.info("单一周期线程池，调用前");
        for (int i = 0; i < 10; i++) {
            singleThreadScheduledExecutor.schedule(() -> {
                try {
                    Thread.sleep(1000);
                    log.info("单一周期线程池，调用时延迟5秒执行一次任务，只执行一次");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, 5, TimeUnit.SECONDS);
        }
    }

    //===============================================================================================

    private static final AtomicInteger ai = new AtomicInteger(1);
    private static final AtomicInteger ai2 = new AtomicInteger(1);

    public static void WorkQueue() throws InterruptedException {
//        threadPoolExecutorA();
//        threadPoolExecutorB();
        threadPoolExecutorC();
    }

    /**
     * 核心3，最大6，超时10秒，SynchronousQueue队列无长度
     * 当前运行线程数<=核心数，直接使用核心数
     * 当前运行线程数>核心数，不会加入任务队列，而是创建非核心线程来执行任务
     * 当前运行线程数>最大数，进行饱和策略(目前策略是直接丢弃)
     * 空闲非核心数，超过空闲时间时移除
     */
    public static void threadPoolExecutorA() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutorA = new ThreadPoolExecutor(
                3,
                6,
                10, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadFactory() {
                    int i = 0;

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "threadA-" + i++);
                    }
                },
                new ThreadPoolExecutor.DiscardPolicy());

        System.out.println("先开三个线程");
        start(threadPoolExecutorA);
        System.out.println("=========================================");
        System.out.println("再开三个线程");
        start(threadPoolExecutorA);
        System.out.println("=========================================");
        System.out.println("再开三个线程");
        start(threadPoolExecutorA);
        System.out.println("=========================================");
        System.out.println("20秒后再开三个线程");
        Thread.sleep(20000);
        start(threadPoolExecutorA);
    }

    /**
     * 核心3，最大6，超时10秒，ArrayBlockingQueue队列长度5
     * 当前运行线程数<=核心数，直接使用核心数
     * 当前运行线程数>核心数，加入任务队列
     * 当前运行线程数>任务队列长度，创建非核心数(核心+非核心<=最大数)
     * 当前运行线程数>最大数，进行饱和策略(目前策略是直接丢弃)
     * 空闲非核心数，超过空闲时间时移除
     */
    public static void threadPoolExecutorB() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutorB = new ThreadPoolExecutor(
                3,
                6,
                10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                new ThreadFactory() {
                    int i = 0;

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "threadB-" + i++);
                    }
                },
                new ThreadPoolExecutor.DiscardPolicy());

        System.out.println("先开三个线程");
        start(threadPoolExecutorB);
        System.out.println("=========================================");
        System.out.println("再开三个线程");
        start(threadPoolExecutorB);
        System.out.println("=========================================");
        System.out.println("再开三个线程");
        start(threadPoolExecutorB);
        System.out.println("=========================================");
        System.out.println("20秒后再开三个线程");
        Thread.sleep(20000);
        start(threadPoolExecutorB);
    }

    /**
     * 核心3，最大6，超时10秒，LinkedBlockingDeque队列长度5
     * 当前运行线程数<=核心数，直接使用核心数
     * 当前运行线程数>核心数，加入任务队列
     * 当前运行线程数>任务队列长度，创建非核心数(核心+非核心<=最大数)
     * 当前运行线程数>最大数，进行饱和策略(目前策略是直接丢弃)
     * 空闲非核心数，超过空闲时间时移除
     */
    public static void threadPoolExecutorC() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutorC = new ThreadPoolExecutor(
                3,
                6,
                10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(5),
                new ThreadFactory() {
                    int i = 0;

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "threadC-" + i++);
                    }
                },
                new ThreadPoolExecutor.DiscardPolicy());

        System.out.println("先开三个线程");
        start(threadPoolExecutorC);
        System.out.println("=========================================");
        System.out.println("再开三个线程");
        start(threadPoolExecutorC);
        System.out.println("=========================================");
        System.out.println("再开三个线程");
        start(threadPoolExecutorC);
        System.out.println("=========================================");
        System.out.println("20秒后再开三个线程");
        Thread.sleep(20000);
        start(threadPoolExecutorC);
    }

    private static void start(ThreadPoolExecutor threadPoolExecutor) {
        for (int i = 1; i <= 3; i++) {
            log.info("创建线程总数：" + ai.getAndIncrement());
            threadPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(3000);
                    log.info("执行线程数：" + ai2.getAndIncrement());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("核心线程数：" + threadPoolExecutor.getCorePoolSize());
        System.out.println("线程池数：" + threadPoolExecutor.getPoolSize());
        System.out.println("队列任务数：" + threadPoolExecutor.getQueue().size());
    }

    //===============================================================================================


}
