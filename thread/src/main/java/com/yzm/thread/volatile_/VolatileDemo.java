package com.yzm.thread.volatile_;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
@NoArgsConstructor
public class VolatileDemo {

    public static void main(String[] args) throws InterruptedException {
//        demo01();
//        demo02();
//        demo03();
        demo04();
//        demo02_4();
    }


    private int a;
    private int b;
    private int c;
    private static int count;

    /**
     * 查看--指令重排序现象
     * t1线程对a，b进行赋值，正常执行流程，a永远比b大，c变量就一直是0
     * 若发生了 指令重排序 即先执行了b = 1，而a还是0；t2或t3线程比较b>a，c++
     */
    public static void demo01() {
        int i = 1;
        do {
            VolatileDemo demo = new VolatileDemo();
            new Thread(demo::method01, "t1").start();
            new Thread(demo::method01_2, "t2").start();

            //等待t1、t2、t3线程执行完毕，再执行main线程
            while (Thread.activeCount() > 1) Thread.yield();
            System.out.println(i++ + "：===============================");
        } while (count == 0);
        System.out.println(i);
        System.out.println(count);
    }

    private void method01() {
        try {
            Thread.sleep(1);
            a = 1;
            b = 2;
            c = 3;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void method01_2() {
        try {
            Thread.sleep(1);
            System.out.println("线程：" + Thread.currentThread().getName() + " ==> a=" + a + " and b=" + b + " and c=" + c);
            if ((c == 3 && a + b != c) || (b == 2 && a != 1)) {
                System.err.println("++++++++++++++++++++++++++++++++线程：" + Thread.currentThread().getName() + " ==> a=" + a + " and b=" + b + " and c=" + c);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //=============================================================================================

    private static int inc;

    /**
     * volatile不适合复合操作
     * 参数inc 加不加上 volatile 都是一样的：不能确保最终结果一致
     */
    private static void demo02() {
        Thread t1 = new Thread(VolatileDemo::method2, "t1");
        Thread t2 = new Thread(VolatileDemo::method2, "t2");
        Thread t3 = new Thread(VolatileDemo::method2, "t3");
        t1.start();
        t2.start();
        t3.start();

        while (Thread.activeCount() > 1) Thread.yield();
        System.out.println(inc);
    }

    private static void method2() {
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

    /**
     * synchronized
     * 参数inc 不加 volatile
     */
    private static void demo03() {
        Thread t1 = new Thread(VolatileDemo::method03, "t1");
        Thread t2 = new Thread(VolatileDemo::method03, "t2");
        Thread t3 = new Thread(VolatileDemo::method03, "t3");
        t1.start();
        t2.start();
        t3.start();

        while (Thread.activeCount() > 1) Thread.yield();
        System.out.println(inc);
    }

    private synchronized static void method03() {
        String thName = Thread.currentThread().getName();
        System.out.println("线程：" + thName + " is ready >> " + inc);
        try {
            Thread.sleep(1000);
            for (int i = 0; i < 1000; i++) {
                inc++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("线程：" + thName + " is over >> " + inc);
    }

    /**
     * ReentrantLock
     * 参数inc 不加 volatile
     */
    private static void demo04() {
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> method04(lock), "t1");
        Thread t2 = new Thread(() -> method04(lock), "t2");
        Thread t3 = new Thread(() -> method04(lock), "t3");
        t1.start();
        t2.start();
        t3.start();

        while (Thread.activeCount() > 1) Thread.yield();
        System.out.println(inc);
    }

    private static void method04(Lock lock) {
        String thName = Thread.currentThread().getName();
        System.out.println("线程：" + thName + " is ready >> " + inc);
        try {
            Thread.sleep(1000);
            lock.lock();
            for (int i = 0; i < 1000; i++) {
                inc++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println("线程：" + thName + " is over >> " + inc);
    }

    /**
     * AtomicInteger
     * 参数inc 不加 volatile
     */
    private static AtomicInteger atomicInc = new AtomicInteger();

    private static void demo05()  {
        Thread t1 = new Thread(VolatileDemo::method05, "t1");
        Thread t2 = new Thread(VolatileDemo::method05, "t2");
        Thread t3 = new Thread(VolatileDemo::method05, "t3");
        t1.start();
        t2.start();
        t3.start();

        while (Thread.activeCount() > 1) Thread.yield();
        System.out.println(atomicInc.get());
    }

    private static void method05() {
        String thName = Thread.currentThread().getName();
        System.out.println("线程：" + thName + " is ready >> " + atomicInc.get());
        try {
            Thread.sleep(1000);
            for (int i = 0; i < 1000; i++) {
                atomicInc.getAndIncrement();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("线程：" + thName + " is over >> " + atomicInc.get());
    }

}
