package com.yzm.thread.cyclic_barrier;

import java.util.concurrent.CountDownLatch;

/**
 * 控制线程启动
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        demo_01();
//        demo_02();
    }

    /**
     * 主线程等待所有子线程的任务完成，才继续往下执行
     * 应用场景就是启动一个服务时，主线程需要等待多个组件加载完毕，之后再继续执行
     */
    private static void demo_01() {
        final CountDownLatch latch = new CountDownLatch(3);

        Thread t1 = new Thread(getRunnable(latch), "t1");
        Thread t2 = new Thread(getRunnable(latch), "t2");
        Thread t3 = new Thread(getRunnable(latch), "t3");

        t1.start();
        t2.start();
        t3.start();

        try {
            latch.await();//阻塞当前线程，直到计数器归零
            System.out.println("所有参赛人员已完成比赛，下面进行统计");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Runnable getRunnable(CountDownLatch latch) {
        return () -> {
            String thName = Thread.currentThread().getName();
            try {
                System.out.println("选手：" + thName + "准备就绪");
                long start = System.currentTimeMillis();
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("选手：" + thName + "跑完全程耗时：" + (System.currentTimeMillis() - start));
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * 赛跑
     * 将多个线程放到起点，等待发令枪响，然后同时开跑
     */
    private static void demo_02() {
        final CountDownLatch referee = new CountDownLatch(1);
        final CountDownLatch players = new CountDownLatch(4);

        Thread t1 = new Thread(getRunnable2(referee, players), "t1");
        Thread t2 = new Thread(getRunnable2(referee, players), "t2");
        Thread t3 = new Thread(getRunnable2(referee, players), "t3");
        Thread t4 = new Thread(getRunnable2(referee, players), "t4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            Thread.sleep(3000);
            System.out.println("裁判：" + Thread.currentThread().getName() + " 开始鸣枪！");
            referee.countDown();
            players.await();
            System.out.println("比赛结束");
            System.out.println("裁判：" + Thread.currentThread().getName() + "汇总成绩排名");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Runnable getRunnable2(CountDownLatch referee, CountDownLatch players) {
        return () -> {
            String thName = Thread.currentThread().getName();
            try {
                System.out.println("选手：" + thName + " 准备就绪");
                referee.await();
                System.out.println("选手：" + thName + " 鸣枪开跑");
                long start = System.currentTimeMillis();
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("选手：" + thName + " 到达终点耗时：" + (System.currentTimeMillis() - start));
                players.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

}
