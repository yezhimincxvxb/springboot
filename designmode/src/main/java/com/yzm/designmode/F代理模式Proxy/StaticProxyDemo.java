package com.yzm.designmode.F代理模式Proxy;

/**
 * 设计模式中定义: 为其他对象提供一种代理以控制对这个对象的访问.
 * 静态代理
 */
public class StaticProxyDemo {

    public static void main(String[] args) {
        //客户端通过代理对象调用，不知道真实对象
        Subject subject = new StaticProxy(new RealSubject());
        //代理对象作为客户端和目标对象之间的中介，起到了保护目标对象的作用
        subject.buyMac();
    }
}

/**
 * 声明目标对象的抽象接口
 */
interface Subject {
    void buyMac();
}

/**
 * 真实对象
 */
class RealSubject implements Subject {
    @Override
    public void buyMac() {
        System.out.println("买Mac电脑");
    }
}

/**
 * 代理对象
 */
class StaticProxy implements Subject {

    private RealSubject targetObject;

    public StaticProxy(RealSubject targetObject) {
        this.targetObject = targetObject;
    }

    @Override
    public void buyMac() {
        this.order();
        targetObject.buyMac();
        this.Ship();
    }

    public void order() {
        System.out.println("确认订单");
    }

    public void Ship() {
        System.out.println("发货");
    }
}