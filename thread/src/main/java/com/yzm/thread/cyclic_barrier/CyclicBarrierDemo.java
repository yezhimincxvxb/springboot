package com.yzm.thread.cyclic_barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierDemo {

    public static void main(String[] args) throws Exception {
//        demo01();
//        demo02();
//        demo03();
//        demo04();
        demo05();
    }

    public static void demo01() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(4);

        Thread t1 = new Thread(getRunnable(barrier), "t1");
        Thread t2 = new Thread(getRunnable(barrier), "t2");
        Thread t3 = new Thread(getRunnable(barrier), "t3");

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(3000);
        System.out.println("所有人准备完毕，开始鸣枪！");
        barrier.await();
    }

    private static Runnable getRunnable(CyclicBarrier barrier) {
        return () -> {
            String thName = Thread.currentThread().getName();
            try {
                System.out.println("选手：" + thName + "准备就绪");
                barrier.await();
                long start = System.currentTimeMillis();
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("选手：" + thName + "跑完全程耗时：" + (System.currentTimeMillis() - start));
            } catch (BrokenBarrierException e) {
                System.out.println(thName + " broken");
            } catch (InterruptedException e) {
                System.out.println(thName + " 线程中断");
            }
        };
    }

    public static void demo02() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(4, () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " 准备鸣枪！");
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + " 开始鸣枪！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t1 = new Thread(getRunnable2(barrier), "t1");
        Thread t2 = new Thread(getRunnable2(barrier), "t2");
        Thread t3 = new Thread(getRunnable2(barrier), "t3");

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(2000);
        barrier.await();
        System.out.println("计数器归零");
    }

    private static Runnable getRunnable2(CyclicBarrier barrier) {
        return () -> {
            String thName = Thread.currentThread().getName();
            try {
                System.out.println("选手：" + thName + "准备就绪");
                barrier.await();
                long start = System.currentTimeMillis();
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("选手：" + thName + "跑完全程耗时：" + (System.currentTimeMillis() - start));
            } catch (BrokenBarrierException e) {
                System.out.println(thName + " broken");
            } catch (InterruptedException e) {
                System.out.println(thName + " 线程中断");
            }
        };
    }

    public static void demo03() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(4);

        Thread t1 = new Thread(getRunnable3(barrier), "t1");
        Thread t2 = new Thread(getRunnable3(barrier), "t2");
        Thread t3 = new Thread(getRunnable3(barrier), "t3");

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(5000);
        System.out.println("所有人准备完毕，开始鸣枪！");
        barrier.await();
    }

    private static Runnable getRunnable3(CyclicBarrier barrier) {
        return () -> {
            String thName = Thread.currentThread().getName();
            try {
                System.out.println("选手：" + thName + "准备就绪");
                if ("t2".equals(Thread.currentThread().getName())) {
                    barrier.await(3000, TimeUnit.MILLISECONDS);
                } else {
                    barrier.await();
                }

                long start = System.currentTimeMillis();
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("选手：" + thName + "跑完全程耗时：" + (System.currentTimeMillis() - start));
            } catch (BrokenBarrierException e) {
                System.out.println(thName + " broken损坏");
            } catch (InterruptedException e) {
                System.out.println(thName + " 线程中断");
            } catch (TimeoutException e) {
                System.out.println(thName + " 等待超时");
            }
        };
    }

    public static void demo04() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(4);

        Thread t1 = new Thread(getRunnable4(barrier), "t1");
        Thread t2 = new Thread(getRunnable4(barrier), "t2");
        Thread t3 = new Thread(getRunnable4(barrier), "t3");

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(1000);
        t2.interrupt();

        Thread.sleep(5000);
        System.out.println("所有人准备完毕，开始鸣枪！");
        barrier.await();
    }

    private static Runnable getRunnable4(CyclicBarrier barrier) {
        return () -> {
            String thName = Thread.currentThread().getName();
            try {
                System.out.println("选手：" + thName + "准备就绪");
                barrier.await();
                long start = System.currentTimeMillis();
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("选手：" + thName + "跑完全程耗时：" + (System.currentTimeMillis() - start));
            } catch (BrokenBarrierException e) {
                System.out.println(thName + " broken损坏");
            } catch (InterruptedException e) {
                System.out.println(thName + " 线程中断");
            }
        };
    }

    public static void demo05() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(4);

        Thread t1 = new Thread(getRunnable5(barrier), "t1");
        Thread t2 = new Thread(getRunnable5(barrier), "t2");
        Thread t3 = new Thread(getRunnable5(barrier), "t3");

        t1.start();
        t2.start();
        t3.start();


        Thread.sleep(5000);
        //System.out.println("重置、更新换代");
        //barrier.reset();
        Thread t4 = new Thread(getRunnable5(barrier), "t4");
        t4.start();
        System.out.println("所有人准备完毕，开始鸣枪！");
        barrier.await();
    }

    private static Runnable getRunnable5(CyclicBarrier barrier) {
        return () -> {
            String thName = Thread.currentThread().getName();
            try {
                System.out.println("选手：" + thName + "准备就绪");
                // 重置
                if ("t2".equals(thName)) {
                    barrier.reset();
                }
                barrier.await();
                long start = System.currentTimeMillis();
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("选手：" + thName + "跑完全程耗时：" + (System.currentTimeMillis() - start));
            } catch (BrokenBarrierException e) {
                System.out.println(thName + " broken损坏");
            } catch (InterruptedException e) {
                System.out.println(thName + " 线程中断");
            }
        };
    }
}
