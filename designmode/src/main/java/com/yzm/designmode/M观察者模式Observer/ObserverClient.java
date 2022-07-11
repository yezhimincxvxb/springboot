package com.yzm.designmode.M观察者模式Observer;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * 观察者模式 又称：发布 / 订阅模式
 * 当1个对象的状态发生改变时，所有依赖于它的对象都将得到通知 & 自动更新对应操作。
 */
public class ObserverClient {
    public static void main(String[] args) {
        //来了一个小丑
        Clown clown = new Clown();
        //观众入场了
        for (int i = 0; i < 10; i++) {
            Viewer v = new Viewer(i);
            clown.addObserver(v);
            System.out.println("座号为"+v.getSeatNo()+"的观众入座");
        }
        //小丑开始表演
        clown.perform();
        //小丑表演完毕，退场
        clown.exit();
    }
}

/**
 * 被观察者角色
 */
class Clown extends Observable {
    /**
     * 表演的精彩
     */
    public static final int PERFORM_GOOD = 0;
    /**
     * 表演的糟糕
     */
    public static final int PERFORM_BAD = 1;
    /**
     * 表演完毕
     */
    public static final int PERFORM_COMPLETE = 2;

    public Clown() {
        System.out.println("**小丑表演，欢迎购票入场**");
    }

    /**
     * 表演
     */
    public void perform() {
        System.out.println("**表演开始，小丑上台！**");

        int random = new Random().nextInt(2);
        //小丑表演状态是随机值，0表演的好，1表演的差
        switch (random) {
            case PERFORM_GOOD:
                System.out.println("**小丑状态很好，表演的很精彩！**");
                break;
            case PERFORM_BAD:
                System.out.println("**小丑状态不好，出了点篓子！**");
                break;
        }
        setChanged();
        notifyObservers(random);//表演好坏通过该参数传递到观众的update方法的第二个参数上
    }

    /**
     * 表演结束,小丑退场
     */
    public void exit() {
        System.out.println("**表演结束，小丑退场！**");
        setChanged();
        notifyObservers(PERFORM_COMPLETE);//退场消息通过该参数传递到观众的update方法的第二个参数上
    }

}

/**
 * 观察者角色
 */
class Viewer implements Observer {
    private int seatNo;

    public Viewer(int seatNo) {
        this.seatNo = seatNo;
    }

    @Override
    public void update(Observable o, Object arg) {
        Integer state = (Integer) arg;
        switch (state) {
            case Clown.PERFORM_GOOD:
                applause();
                break;
            case Clown.PERFORM_BAD:
                CheerBack();
                break;
            case Clown.PERFORM_COMPLETE:
                exit();
                break;
            default:
                break;
        }
    }

    /**
     * 鼓掌
     */
    private void applause() {
        System.out.println("座位号" + getSeatNo() + "的观众鼓掌了！");
    }

    /**
     * 倒喝彩
     */
    private void CheerBack() {
        System.out.println("座位号" + getSeatNo() + "的观众发出了倒喝彩！");
    }

    /**
     * 退场
     */
    private void exit() {
        System.out.println("座位号" + getSeatNo() + "的观众退场！");
    }

    public int getSeatNo() {
        return seatNo;
    }

}