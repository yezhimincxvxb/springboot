package com.yzm.designmode.B工厂模式Factory;

/**
 * 工厂模式：提供创建对象的接口
 * 简单工厂模式
 */
public class SimpleFactory {

    public static Simple create(String name) {
        if ("A".equals(name)) {
            return new SimpleA();
        } else if ("B".equals(name)) {
            return new SimpleB();
        }
        return null;
    }

    public static void main(String[] args) {
        Simple a = SimpleFactory.create("A");
        Simple b = SimpleFactory.create("B");
        a.doing();
        b.doing();
    }

}

interface Simple {
    void doing();
}

class SimpleA implements Simple{

    @Override
    public void doing() {
        System.out.println("SimpleA");
    }
}

class SimpleB implements Simple{

    @Override
    public void doing() {
        System.out.println("SimpleB");
    }
}