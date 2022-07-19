package com.yzm.designmode.BehavioralModel行为型模式.ChainOfResponsibility职责链;

import java.util.Random;

/**
 * 职责链模式
 */
public class ChainClient {
    public static void main(String[] args) throws InterruptedException {
        IHandler h1 = new FirstHandler();
        IHandler h2 = new SecondHandler();
        IHandler h3 = new ThirdHandler();
        IHandler h4 = new FourthHandler();

        h1.setHandler(h2);
        h2.setHandler(h3);
        h3.setHandler(h4);

        for (int i = 1; i <= 5; i++) {
            System.out.println("第" + i + "挑战");
            long time = h1.breakThrough("张大炮", System.currentTimeMillis());
            System.out.println("耗时：" + time);
            System.out.println();
        }
    }
}

abstract class IHandler {
    protected IHandler handler;

    public void setHandler(IHandler handler) {
        this.handler = handler;
    }

    public abstract long breakThrough(String username, long time) throws InterruptedException;
}

class FirstHandler extends IHandler {

    @Override
    public long breakThrough(String username, long time) throws InterruptedException {
        System.out.println("-----欢迎来到第一关-----");
        Thread.sleep(new Random().nextInt(1000));
        if (new Random().nextInt(100) % 2 == 0) {
            System.out.println("恭喜" + username + "通关成功");
            if (handler != null) {
                handler.breakThrough(username, time);
            } else {
                System.out.println("恭喜" + username + "闯关成功！");
            }
        } else {
            System.out.println("很遗憾，" + username + "闯关失败，获得参与奖水杯一个！");
        }
        time = System.currentTimeMillis() - time;
        return time;
    }
}

class SecondHandler extends IHandler {

    @Override
    public long breakThrough(String username, long time) throws InterruptedException {
        System.out.println("-----欢迎来到第二关-----");
        Thread.sleep(new Random().nextInt(1000));
        if (new Random().nextInt(100) % 2 == 0) {
            System.out.println("恭喜" + username + "通关成功");
            if (handler != null) {
                handler.breakThrough(username, time);
            } else {
                System.out.println("恭喜" + username + "闯关成功！");
            }
        } else {
            System.out.println("很遗憾，" + username + "闯关失败，获得安慰奖雨伞一把！");
        }
        time = System.currentTimeMillis() - time;
        return time;
    }
}

class ThirdHandler extends IHandler {

    @Override
    public long breakThrough(String username, long time) throws InterruptedException {
        System.out.println("-----欢迎来到第三关-----");
        Thread.sleep(new Random().nextInt(1000));
        if (new Random().nextInt(100) % 2 == 0) {
            System.out.println("恭喜" + username + "通关成功");
            if (handler != null) {
                handler.breakThrough(username, time);
            } else {
                System.out.println("恭喜" + username + "闯关成功，闯关结束！");
            }
        } else {
            System.out.println("很遗憾，" + username + "闯关失败，获得三等奖冰箱一台！");
        }
        time = System.currentTimeMillis() - time;
        return time;
    }
}

class FourthHandler extends IHandler {

    @Override
    public long breakThrough(String username, long time) throws InterruptedException {
        System.out.println("-----欢迎来到第四关-----");
        Thread.sleep(new Random().nextInt(1000));
        if (new Random().nextInt(100) % 2 == 0) {
            System.out.println("恭喜" + username + "通关成功");
            if (handler != null) {
                handler.breakThrough(username, time);
            } else {
                System.out.println("恭喜" + username + "闯关成功，获得一等奖2000现金！");
            }
        } else {
            System.out.println("很遗憾，" + username + "闯关失败，获得二等奖洗衣机一台！");
        }
        time = System.currentTimeMillis() - time;
        return time;
    }
}