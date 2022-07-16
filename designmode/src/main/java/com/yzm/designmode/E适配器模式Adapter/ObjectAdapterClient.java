package com.yzm.designmode.E适配器模式Adapter;

/**
 * 对象适配器模式
 */
public class ObjectAdapterClient {
    public static void main(String[] args) {
        //保留原有功能
        Target2 target2 = new ConcreteTarget2();
        target2.request();

        Target2 adapter2 = new ConcreteAdapter2(new Adaptee2());
        adapter2.request();
    }

}

/**
 * 目标接口、标准接口
 * 已有的
 */
interface Target2 {
    void request();
}

/**
 * 已有具体目标类，只提供普通功能
 * 已有的
 */
class ConcreteTarget2 implements Target2 {
    public void request() {
        System.out.println("原有实现类 具有 普通功能...");
    }
}

/**
 * 被适配者类，具有特殊功能、但不符合我们既有的标准接口(不能通过Target调用)
 * 已有的
 */
class Adaptee2 {
    public void specificRequest() {
        System.out.println("原有被适配类 具有 特殊功能...");
    }
}

/**
 * 新增适配者
 * 实现目标接口并且注入被适配者对象，使得可以通过Target调用具有特殊功能的被适配者类
 */
class ConcreteAdapter2 implements Target2 {

    private final Adaptee2 adaptee2;

    public ConcreteAdapter2(Adaptee2 adaptee2) {
        this.adaptee2 = adaptee2;
    }

    @Override
    public void request() {
        System.out.println("新增适配类，使目标接口可以调用被适配者类");
        adaptee2.specificRequest();
    }
}