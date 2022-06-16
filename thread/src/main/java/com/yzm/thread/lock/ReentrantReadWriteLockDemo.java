package com.yzm.thread.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) throws Exception {
//        demo01();
//        demo02();
//        demo03();
        demo04();
    }

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final AtomicInteger ai = new AtomicInteger(1);

    /**
     * 读锁：可以被多个线程同时持有
     */
    public static void demo01() {
        Thread t1 = new Thread(ReentrantReadWriteLockDemo::read, "t1");
        Thread t2 = new Thread(ReentrantReadWriteLockDemo::read, "t2");
        Thread t3 = new Thread(ReentrantReadWriteLockDemo::read, "t3");

        t1.start();
        t2.start();
        t3.start();
    }

    private static void read() {
        String thName = Thread.currentThread().getName();
        try {
            System.out.println("线程：" + thName + " 准备就绪");
            Thread.sleep(1000);
            lock.readLock().lock();
            System.out.println("线程：" + thName + " 持有读锁");
            for (int i = 0; i < 10; i++) {
                System.out.println("线程：" + thName + " 正在进行读操作 ==> " + ai.getAndIncrement());
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
            System.out.println("线程：" + thName + " 释放读锁");
        }
    }

    /**
     * 写锁：独占，只能被一个线程持有
     */
    public static void demo02() {
        Thread t1 = new Thread(ReentrantReadWriteLockDemo::write, "t1");
        Thread t2 = new Thread(ReentrantReadWriteLockDemo::write, "t2");
        Thread t3 = new Thread(ReentrantReadWriteLockDemo::write, "t3");

        t1.start();
        t2.start();
        t3.start();
    }

    private static void write() {
        String thName = Thread.currentThread().getName();
        try {
            System.out.println("线程：" + thName + " 准备就绪");
            Thread.sleep(1000);
            lock.writeLock().lock();
            System.out.println("线程：" + thName + " 持有写锁");
            for (int i = 0; i < 10; i++) {
                System.out.println("线程：" + thName + " 正在进行写操作 ==> " + ai.getAndIncrement());
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
            System.out.println("线程：" + thName + " 释放写锁");
        }
    }

    /**
     * 如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待，直到读锁释放。
     * 如果有一个线程已经占用了写锁，则此时其他线程无论申请写锁还是读锁，申请的线程都一直等待，直到写锁释放。
     */
    public static void demo03() throws InterruptedException {
        Thread t1 = new Thread(ReentrantReadWriteLockDemo::read, "t1");
        Thread t2 = new Thread(ReentrantReadWriteLockDemo::write, "t2");
        Thread t3 = new Thread(ReentrantReadWriteLockDemo::read, "t3");

        t1.start();
        Thread.sleep(100);
        t2.start();
        t3.start();
    }

    private static void demo04() throws InterruptedException {
        Thread t1 = new Thread(ReentrantReadWriteLockDemo::write, "t1");
        Thread t2 = new Thread(ReentrantReadWriteLockDemo::read, "t2");
        Thread t3 = new Thread(ReentrantReadWriteLockDemo::write, "t3");

        t1.start();
        Thread.sleep(100);
        t2.start();
        t3.start();
    }

}
