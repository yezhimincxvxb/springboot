package com.yzm.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    public static void main(String[] args) throws Exception {
//        demo01();
//        demo02();
        demo03();
//        demo04();
//        demo05();
    }

    public static void demo01() throws InterruptedException {
        ReentrantLock nonFairLock = new ReentrantLock();

        Thread t1 = new Thread(getRunnable(nonFairLock), "t1");
        Thread t2 = new Thread(getRunnable(nonFairLock), "t2");
        Thread t3 = new Thread(getRunnable(nonFairLock), "t3");

        Thread.sleep(3000);
        t1.start();
        t2.start();
        t3.start();
    }

    private static Runnable getRunnable(ReentrantLock nonFairLock) {
        return () -> {
            String thName = Thread.currentThread().getName();
            System.out.println("线程：" + thName + " 竞争锁");
            try {
                nonFairLock.lock();
                System.out.println("线程：" + thName + " 获取锁，执行任务");
                long start = System.currentTimeMillis();
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("线程：" + thName + " 完成任务耗时：" + (System.currentTimeMillis() - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("线程：" + thName + " 释放锁=============================");
                nonFairLock.unlock();
            }
        };
    }

    public static void demo02() throws InterruptedException {
        ReentrantLock nonFairLock = new ReentrantLock();

        Thread t1 = new Thread(getRunnable2(nonFairLock), "t1");
        Thread t2 = new Thread(getRunnable2(nonFairLock), "t2");
        Thread t3 = new Thread(getRunnable2(nonFairLock), "t3");

        Thread.sleep(3000);
        t1.start();
        t2.start();
        t3.start();
    }

    private static Runnable getRunnable2(ReentrantLock nonFairLock) {
        return () -> {
            String thName = Thread.currentThread().getName();
            System.out.println("线程：" + thName + " 竞争锁");
            for (int i = 0; i < 2; i++) {
                try {
                    nonFairLock.lock();
                    System.out.println("线程：" + thName + " 获取锁，执行任务");
                    long start = System.currentTimeMillis();
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程：" + thName + " 完成任务耗时：" + (System.currentTimeMillis() - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("线程：" + thName + " 释放锁=============================");
                    nonFairLock.unlock();
                }
            }
        };
    }

    public static void demo03() throws InterruptedException {
        ReentrantLock fairLock = new ReentrantLock(true);

        Thread t1 = new Thread(getRunnable3(fairLock), "t1");
        Thread t2 = new Thread(getRunnable3(fairLock), "t2");
        Thread t3 = new Thread(getRunnable3(fairLock), "t3");

        Thread.sleep(3000);
        t1.start();
        t2.start();
        t3.start();
    }

    private static Runnable getRunnable3(ReentrantLock fairLock) {
        return () -> {
            String thName = Thread.currentThread().getName();
            System.out.println("线程：" + thName + " 竞争锁");
            for (int i = 0; i < 3; i++) {
                try {
                    fairLock.lock();
                    System.out.println("线程：" + thName + " 获取锁，执行任务");
                    long start = System.currentTimeMillis();
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("线程：" + thName + " 完成任务耗时：" + (System.currentTimeMillis() - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("线程：" + thName + " 释放锁=============================");
                    fairLock.unlock();
                }
            }
        };
    }

    public static void demo04() throws InterruptedException {
        ReentrantLock nonFairLock = new ReentrantLock();

        Thread t1 = new Thread(getRunnable4(nonFairLock), "t1");
        Thread t2 = new Thread(getRunnable4(nonFairLock), "t2");
        Thread t3 = new Thread(getRunnable4(nonFairLock), "t3");

        Thread.sleep(3000);
        t1.start();
        t2.start();
        t3.start();
    }

    private static Runnable getRunnable4(ReentrantLock nonFairLock) {
        return () -> {
            String thName = Thread.currentThread().getName();
            System.out.println("线程：" + thName + " 竞争锁");
            try {
                while (true) {
                    if (nonFairLock.tryLock(5000, TimeUnit.MILLISECONDS)) {
                        System.out.println("线程：" + thName + " 获取锁，执行任务");
                        long start = System.currentTimeMillis();
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("线程：" + thName + " 完成任务耗时：" + (System.currentTimeMillis() - start));
                        break;
                    } else {
                        System.out.println("线程：" + thName + " 锁已被持有，先干点别的吧");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("线程：" + thName + " 释放锁=============================");
                nonFairLock.unlock();
            }
        };
    }

    public static void demo05() throws InterruptedException {
        ReentrantLock nonFairLock = new ReentrantLock();

        Thread t1 = new Thread(getRunnable5(nonFairLock), "t1");
        Thread t2 = new Thread(getRunnable5(nonFairLock), "t2");

        Thread.sleep(3000);
        t1.start();
        t2.start();

        Thread.sleep(1000);
        t2.interrupt();
    }

    private static Runnable getRunnable5(ReentrantLock nonFairLock) {
        return () -> {
            String thName = Thread.currentThread().getName();
            System.out.println("线程：" + thName + " 竞争锁");
            try {
                nonFairLock.lockInterruptibly();
                System.out.println("线程：" + thName + " 获取锁，执行任务");
                long start = System.currentTimeMillis();
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("线程：" + thName + " 完成任务耗时：" + (System.currentTimeMillis() - start));
            } catch (InterruptedException e) {
                System.out.println("线程：" + thName + " 线程中断");
            } finally {
                System.out.println("线程：" + thName + " 释放锁=============================");
                nonFairLock.unlock();
            }
        };
    }
}
