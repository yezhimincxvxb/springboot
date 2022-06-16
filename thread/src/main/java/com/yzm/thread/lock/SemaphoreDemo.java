package com.yzm.thread.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {

    public static void main(String[] args) throws InterruptedException {
//        demo01();
//        demo02();
//        demo03();
        demo04();
    }

    public static void demo01() throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        Thread t1 = new Thread(() -> method01(semaphore), "t1");
        Thread t2 = new Thread(() -> method01(semaphore), "t2");
        Thread t3 = new Thread(() -> method01(semaphore), "t3");

        t1.start();
        t2.start();
        t3.start();
    }

    private static void method01(Semaphore semaphore) {
        String thName = Thread.currentThread().getName();
        try {
            long start = System.currentTimeMillis();
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println("线程: " + thName + "执行acquire方法耗时：" + (System.currentTimeMillis() - start));
            Thread.sleep(1000);
            semaphore.acquire();
            System.out.println("线程: " + thName + "获得许可");

            long start2 = System.currentTimeMillis();
            Thread.sleep((long) (Math.random() * 3000));
            System.out.println("线程: " + thName + "任务耗时：" + (System.currentTimeMillis() - start2));

            Thread.sleep(1000);
            System.err.println("线程: " + thName + "释放许可");
            semaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void demo02() throws InterruptedException {
        Semaphore semaphore = new Semaphore(2);
        Thread t1 = new Thread(() -> method02(semaphore), "t1");
        Thread t2 = new Thread(() -> method02(semaphore), "t2");
        Thread t3 = new Thread(() -> method02(semaphore), "t3");
        Thread t4 = new Thread(() -> method02(semaphore), "t4");
        Thread t5 = new Thread(() -> method02(semaphore), "t5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }

    private static void method02(Semaphore semaphore) {
        String thName = Thread.currentThread().getName();
        try {
            long start = System.currentTimeMillis();
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println("线程: " + thName + "执行acquire方法耗时：" + (System.currentTimeMillis() - start));
            Thread.sleep(1000);
            semaphore.acquire(2);
            System.out.println("线程: " + thName + "获得许可");

            long start2 = System.currentTimeMillis();
            Thread.sleep((long) (Math.random() * 3000));
            System.out.println("线程: " + thName + "任务耗时：" + (System.currentTimeMillis() - start2));

            Thread.sleep(1000);
            System.err.println("线程: " + thName + "释放许可");
            semaphore.release(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void demo03() throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        Thread t1 = new Thread(() -> method03_2(semaphore), "t1");
        Thread t2 = new Thread(() -> method03_2(semaphore), "t2");
        Thread t3 = new Thread(() -> method03_2(semaphore), "t3");

        t1.start();
        t2.start();
        t3.start();
    }

    private static void method03_1(Semaphore semaphore) {
        String thName = Thread.currentThread().getName();
        try {
            long start = System.currentTimeMillis();
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println("线程: " + thName + "执行acquire方法耗时：" + (System.currentTimeMillis() - start));
            Thread.sleep(1000);
            while (true) {
                if (semaphore.tryAcquire()) {
                    System.out.println("线程: " + thName + "获得许可");

                    long start2 = System.currentTimeMillis();
                    Thread.sleep((long) (Math.random() * 3000));
                    System.out.println("线程: " + thName + "任务耗时：" + (System.currentTimeMillis() - start2));

                    Thread.sleep(1000);
                    System.err.println("线程: " + thName + "释放许可");
                    semaphore.release();
                    break;
                } else {
                    System.out.println("线程: " + thName + "获取许可失败");
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void method03_2(Semaphore semaphore) {
        String thName = Thread.currentThread().getName();
        try {
            long start = System.currentTimeMillis();
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println("线程: " + thName + "执行acquire方法耗时：" + (System.currentTimeMillis() - start));
            Thread.sleep(1000);
            if (semaphore.tryAcquire(5000, TimeUnit.MILLISECONDS)) {
                System.out.println("线程: " + thName + "获得许可");

                long start2 = System.currentTimeMillis();
                Thread.sleep((long) (Math.random() * 3000));
                System.out.println("线程: " + thName + "任务耗时：" + (System.currentTimeMillis() - start2));

                Thread.sleep(1000);
                System.err.println("线程: " + thName + "释放许可");
                semaphore.release();
            } else {
                System.err.println("线程: " + thName + "超时了");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void demo04() throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        Thread t1 = new Thread(() -> method04(semaphore), "t1");
        Thread t2 = new Thread(() -> method04(semaphore), "t2");
        Thread t3 = new Thread(() -> method04(semaphore), "t3");

        t1.start();
        t3.start();
        Thread.sleep(100);
        t2.start();
        Thread.sleep(100);
        t2.interrupt();
    }

    private static void method04(Semaphore semaphore) {
        String thName = Thread.currentThread().getName();
        try {
            semaphore.acquireUninterruptibly();
            System.out.println("线程: " + thName + "获得许可");

            long start2 = System.currentTimeMillis();
            Thread.sleep((long) (Math.random() * 3000));
            System.out.println("线程: " + thName + "任务耗时：" + (System.currentTimeMillis() - start2));

            Thread.sleep(1000);
            System.err.println("线程: " + thName + "释放许可");
            semaphore.release();
        } catch (InterruptedException e) {
            System.err.println("线程: " + thName + "被中断了");
        }
    }

}
