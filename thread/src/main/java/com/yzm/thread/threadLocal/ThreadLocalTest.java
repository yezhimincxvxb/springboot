package com.yzm.thread.threadLocal;

public class ThreadLocalTest {

    static class MyThreadLocal extends ThreadLocal<Integer> {

        @Override
        protected Integer initialValue() {
            return 0;
        }
    }

    public static void main(String[] args) {

        ThreadLocal<Integer> threadLocal = new MyThreadLocal();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    threadLocal.set(threadLocal.get() + 1);
                    System.out.println("当前线程：" + Thread.currentThread().getName() + "值为：" + threadLocal.get());
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    threadLocal.set(threadLocal.get() + 1);
                    System.out.println("当前线程：" + Thread.currentThread().getName() + "值为：" + threadLocal.get());
                }
            }
        }, "t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 4; i++) {
                    threadLocal.set(threadLocal.get() + 1);
                    System.out.println("当前线程：" + Thread.currentThread().getName() + "值为：" + threadLocal.get());
                }
            }
        }, "t3");

        t1.start();
        t2.start();
        t3.start();
    }

}
