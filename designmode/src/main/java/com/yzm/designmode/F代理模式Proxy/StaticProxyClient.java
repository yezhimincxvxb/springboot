package com.yzm.designmode.F代理模式Proxy;

/**
 * 设计模式中定义: 为其他对象提供一种代理以控制对这个对象的访问.
 * 静态代理
 */
public class StaticProxyClient {

    public static void main(String[] args) {
        //自己买
        Subject realSubject = new ConcreteSubject();
        realSubject.buyMac();
        System.out.println();

        //通过代理对象调用，不知道真实对象
        Subject proxySubject = new StaticProxy(new ConcreteSubject());
        //代理对象作为客户端和目标对象之间的中介，起到了保护目标对象的作用
        proxySubject.buyMac();
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
class ConcreteSubject implements Subject {
    @Override
    public void buyMac() {
        System.out.println("买Mac电脑");
    }
}

/**
 * 代理对象
 */
class StaticProxy implements Subject {

    private final ConcreteSubject targetObject;

    public StaticProxy(ConcreteSubject targetObject) {
        this.targetObject = targetObject;
    }

    @Override
    public void buyMac() {
        this.order();
        targetObject.buyMac();
        this.Ship();
    }

    public void order() {
        System.out.println("你好！我是代购。请问需要什么服务？");
    }

    public void Ship() {
        System.out.println("好的，已了解你的购买需求，我们会尽快发货并通知你");
    }
}