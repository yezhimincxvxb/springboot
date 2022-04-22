package com.yzm.thread.keyword;

import lombok.SneakyThrows;

public class SynchronizedDemo {

    public static void main(String[] args) throws Exception {
//        demo01();
//        demo02();
//        demo02_2();
//        demo02_3();
//        demo03();
//        demo04();
//        demo05();
        demo06();
    }

    //=======================================================================================================

    //共享变量
    private static int inc = 0;
    private static final SynchronizedDemo demo = new SynchronizedDemo();

    /**
     * 不加 synchronized 处理
     * 多次测试，不能保证最终结果一致
     */
    private static void demo01()  {
        Thread t1 = new Thread(demo::method01, "t1");
        Thread t2 = new Thread(demo::method01, "t2");
        Thread t3 = new Thread(demo::method01, "t3");
        t1.start();
        t2.start();
        t3.start();

        // 线程礼让，子线程跑完，主线程才跑
        while (Thread.activeCount() > 2) Thread.yield();
        System.out.println("inc =" + inc);
    }

    private void method01() {
        common();
    }

    private static void common() {
        String thName = Thread.currentThread().getName();
        System.out.println("线程：" + thName + " is ready >> " + inc);
        try {
            Thread.sleep(1000);
            for (int i = 0; i < 1000; i++) {
                inc++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程：" + thName + " is over >> " + inc);
    }


    //=======================================================================================================
    /**
     * synchronized作用于实例方法
     * 当两个线程同时对一个对象的一个方法进行操作，只有一个线程能够抢到锁。因为一个对象只有一把锁，一个线程获取了该对象的锁之后，其他线程无法获取该对象的锁，
     * <p>
     * 2个线程一定是一个先完全执行完，另一个线程才会开始执行
     * 能保证最终结果一致
     */
    private static void demo02() {
        Thread t1 = new Thread(demo::method02, "t1");
        Thread t2 = new Thread(demo::method02, "t2");
        Thread t3 = new Thread(demo::method02, "t3");
        t1.start();
        t2.start();
        t3.start();

        while (Thread.activeCount() > 1) Thread.yield();
        System.out.println("inc =" + inc);
    }

    private synchronized void method02() {
        common();
    }

    //=======================================================================================================

    /**
     * 一个线程获取了该对象的锁之后，其他线程可以访问其他非synchronized实例方法(method01 非synchronized，同时交替运行)
     * 一个线程获取了该对象的锁之后，其他线程无法访问其他synchronized实例方法(method02 synchronized)
     */
    private static void demo02_2() {
        Thread t1 = new Thread(demo::method01, "t1");
        Thread t2 = new Thread(demo::method02, "t2");
        t1.start();
        t2.start();

        while (Thread.activeCount() > 1) Thread.yield();
        System.out.println("inc =" + inc);
    }

    //=======================================================================================================

    /**
     * 多个线程作用于不同的对象
     * 因为两个线程作用于不同的对象，获得的是不同的锁，所以互相并不影响
     */
    private static void demo02_3() {
        SynchronizedDemo demo2 = new SynchronizedDemo();
        Thread t1 = new Thread(demo::method02, "t1");
        Thread t2 = new Thread(demo2::method02, "t2");

        t1.start();
        t2.start();

        while (Thread.activeCount() > 1) Thread.yield();
        System.out.println("inc =" + inc);
    }

    //=======================================================================================================

    /**
     * synchronized作用于静态方法
     */
    private static void demo03() {
        Thread t1 = new Thread(SynchronizedDemo::method03, "t1");
        Thread t2 = new Thread(SynchronizedDemo::method03, "t2");
        Thread t3 = new Thread(SynchronizedDemo::method03, "t3");
        t1.start();
        t2.start();
        t3.start();

        while (Thread.activeCount() > 1) Thread.yield();
        System.out.println("inc =" + inc);
    }

    private static synchronized void method03() {
        common();
    }

    //=======================================================================================================

    /**
     * synchronized作用于代码块
     */
    private static void demo04() {
        Thread t1 = new Thread(demo::method04, "t1");
        Thread t2 = new Thread(demo::method04, "t2");
        Thread t3 = new Thread(demo::method04, "t3");
        t1.start();
        t2.start();
        t3.start();

        while (Thread.activeCount() > 1) Thread.yield();
        System.out.println("inc =" + inc);
    }

    private void method04() {
        String thName = Thread.currentThread().getName();
        System.out.println("线程：" + thName + " is ready >> " + inc);
        try {
            Thread.sleep(1000);
            synchronized (this) {
                System.out.println("线程：" + thName + " 持有锁");
                for (int i = 0; i < 1000; i++) {
                    inc++;
                }
                System.out.println("线程：" + thName + " 释放锁");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("线程：" + thName + " is over >> " + inc);
    }

    //=======================================================================================================

    /**
     * synchronized 的可重入性
     * <p>
     * 在获取当前实例对象锁后进入synchronized代码块执行同步代码，并在代码块中调用了当前实例对象的另外一个synchronized方法，
     * 再次请求当前实例锁时，将被允许，进而执行方法体代码，这就是重入锁最直接的体现，需要特别注意另外一种情况，当子类继承父类时，
     * 子类也是可以通过可重入锁调用父类的同步方法。注意由于synchronized是基于monitor实现的，因此每次重入，monitor中的计数器仍会加1。
     */
    private static void demo05() {
        Thread t1 = new Thread(demo::method05, "t1");
        Thread t2 = new Thread(demo::method05, "t2");
        Thread t3 = new Thread(demo::method05, "t3");
        t1.start();
        t2.start();
        t3.start();
    }

    private synchronized void method05() {
        System.out.println("线程：" + Thread.currentThread().getName() + " 执行了method05");
        method05_2();
    }

    private synchronized void method05_2() {
        System.out.println("线程：" + Thread.currentThread().getName() + " 执行了method05_2");
    }

    //=======================================================================================================

    private static void demo06() {
        SyncDemo sync = new SyncDemo();
        Thread t1 = new Thread(sync::method06, "t1");
        Thread t2 = new Thread(sync::method06, "t2");
        Thread t3 = new Thread(sync::method06, "t3");
        t1.start();
        t2.start();
        t3.start();
    }

    public synchronized void method06() {
        System.out.println("线程：" + Thread.currentThread().getName() + " 执行了父方法");
    }

    static class SyncDemo extends SynchronizedDemo {
        public synchronized void method06() {
            System.out.println("线程：" + Thread.currentThread().getName() + " 执行了子方法");
            super.method06();
        }
    }

}
