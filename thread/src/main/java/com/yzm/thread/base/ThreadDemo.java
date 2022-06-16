package com.yzm.thread.base;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadDemo {

    public static void main(String[] args) throws Exception {
//        demo01();
//        demo02();
//        demo03();
        demo04();
    }

    static class MyThread extends Thread {

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println("线程：" + Thread.currentThread().getName() + " 开始执行！！！");
        }
    }

    static class MyThread2 implements Runnable {

        @Override
        public void run() {
            System.out.println("线程：" + Thread.currentThread().getName() + " 开始执行！！！");
        }
    }

    static class MyThread3 implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("线程：" + Thread.currentThread().getName() + " 开始执行！！！");
            return 1;
        }
    }

    private static void demo01() throws ExecutionException, InterruptedException {
        MyThread t1 = new MyThread("t1");
        t1.start();

        Thread t2 = new Thread(new MyThread2(), "t2");
        t2.start();

        Thread t22 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程：" + Thread.currentThread().getName() + " 开始执行！！！");
            }
        }, "t22");
        t22.start();

        MyThread3 callable = new MyThread3();
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        Thread t3 = new Thread(futureTask, "t3");
        t3.start();
        System.out.println("futureTask = " + futureTask.get());
    }

    /**
     * 主线程等待线程的终止。也就是说主线程的代码块中，如果碰到了t.join()方法，此时主线程需要等待（阻塞），
     * 等待线程结束了，才能继续执行t.join()之后的代码块。
     */
    private static void demo02() {
        Thread t1 = new Thread(() -> {
            System.out.println("线程：" + Thread.currentThread().getName() + " >> ready");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程：" + Thread.currentThread().getName() + " >> over");
        }, "t1");

        t1.start();
        try {
            System.out.println("main线程等待t1线程执行");
            t1.join();
            // 带参数，则main线程等待一段时间，超时就不理线程了自己继续执行
            // t1.join(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("main线程执行完毕");

    }


    private static final ThreadDemo demo = new ThreadDemo();

    /**
     * 控制线程间的执行顺序
     */
    private static void demo03() {
        Thread t1 = new Thread(demo::method03, "t1");
        Thread t2 = new Thread(demo::method031, "t2");

        t1.start();
        t2.start();
    }

    private void method03() {
        String name = Thread.currentThread().getName();
        System.out.println("线程：" + name + " is ready");
        try {
            Thread.sleep(10);
            synchronized (demo) {
                System.out.println("线程：" + name + "--> 获取锁");
                Thread.sleep(3000);
                System.out.println("线程：" + name + "--> 执行wait方法，线程挂起，释放锁，等待被唤醒");
                demo.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程：" + name + " is over");
    }

    private void method031() {
        String name = Thread.currentThread().getName();
        System.out.println("线程：" + name + " is ready");
        try {
            Thread.sleep(50);
            synchronized (demo) {
                System.out.println("线程：" + name + "--> 获取锁");
                Thread.sleep(3000);
                System.out.println("线程：" + name + "--> 执行notifyAll方法，唤醒其他线程并调用wait方法使自己挂起");
                demo.notifyAll();
            }
            //执行同步代码块后自动释放锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程：" + name + " is over");
    }

    // 定义生产最大量
    private int productCount = 0;

    private static void demo04() {
        // 创建t1线程，生产资源
        Thread t1 = new Thread(demo::demo04_product, "t1");
        // 创建t2线程，消费资源
        Thread t2 = new Thread(demo::demo04_customer, "t2");
        Thread t3 = new Thread(demo::demo04_customer, "t3");

        // 启动线程
        t1.start();
        t2.start();
        t3.start();
    }

    private synchronized void demo04_product() {
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":::生产:::" + (++productCount));
                Thread.sleep(300);
                if (productCount >= 20) {
                    System.out.println("货舱已满...不必再生产");
                    notifyAll();
                    wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private synchronized void demo04_customer() {
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":::消费:::" + productCount);
                Thread.sleep(300);
                if (productCount <= 1) {
                    productCount = 0;
                    System.out.println("货舱已无货...无法消费");
                    notifyAll();
                    wait();
                } else productCount--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
