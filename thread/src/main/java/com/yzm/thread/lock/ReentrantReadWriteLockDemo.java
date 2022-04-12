package com.yzm.thread.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
//        demo01();
//        demo02();
//        demo03();
        demo04();
    }

    private static final ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 读锁：可以被多个线程同时持有
     */
    private static void demo01() {
        new Thread(() -> demo.method01(1000), "t1").start();
        new Thread(() -> demo.method01(1000), "t2").start();
    }

    private void method01(long time) {
        System.out.println("线程：" + Thread.currentThread().getName() + " is ready");
        try {
            Thread.sleep(time);
            lock.readLock().lock();
            System.out.println("线程：" + Thread.currentThread().getName() + " 持有读锁");
            for (int i = 1; i <= 50; i++) {
                System.out.println("线程：" + Thread.currentThread().getName() + " 正在进行读操作 ==> " + i);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("线程：" + Thread.currentThread().getName() + " 释放读锁");
            lock.readLock().unlock();
        }
    }

    /**
     * 写锁：独占，只能被一个线程持有
     */
    private static void demo02() {
        new Thread(() -> demo.method02(1000), "t1").start();
        new Thread(() -> demo.method02(1000), "t2").start();
    }

    private void method02(long time) {
        System.out.println("线程：" + Thread.currentThread().getName() + " is ready");
        try {
            Thread.sleep(time);
            lock.writeLock().lock();
            System.out.println("线程：" + Thread.currentThread().getName() + " 持有写锁");
            for (int i = 1; i <= 50; i++) {
                System.out.println("线程：" + Thread.currentThread().getName() + " 正在进行写操作 ==> " + i);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("线程：" + Thread.currentThread().getName() + " 释放写锁");
            lock.writeLock().unlock();
        }
    }

    /**
     * 如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待，直到读锁释放。
     * 如果有一个线程已经占用了写锁，则此时其他线程无论申请写锁还是读锁，申请的线程都一直等待，直到写锁释放。
     */
    private static void demo03() {
        // 持有读锁，申请写锁
        new Thread(() -> demo.method01(1000), "t1").start();
        new Thread(() -> demo.method02(2000), "t2").start();
    }

    private static void demo04() {
        // 持有写锁，申请读锁
        new Thread(() -> demo.method01(2000), "t1").start();
        new Thread(() -> demo.method02(1000), "t2").start();
    }

}
