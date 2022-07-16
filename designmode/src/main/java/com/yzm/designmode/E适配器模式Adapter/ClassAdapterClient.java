package com.yzm.designmode.E适配器模式Adapter;

/**
 * 适配器模式定义:
 * 将两个不兼容的类纠合在一起使用，属于结构型模式,需要有 Adaptee(被适配者)和 Adaptor(适配器)两个身份
 *
 * 分类：类的适配器模式(通过继承实现)、对象的适配器模式(通过注入或创建被适配者)
 *
 * 类的适配器模式
 * 把一个类的接口变换成客户端所期待的另一种接口，从而使原本接口不匹配而无法一起工作的两个类能够在一起工作。
 */
public class ClassAdapterClient {
    public static void main(String[] args) {
        //保留原有功能
        Target target = new ConcreteTarget();
        target.request();

        //又能增加新功能
        Target adapter = new ConcreteAdapter();
        adapter.request();
    }
}

/**
 * 目标接口、标准接口
 * 已有的
 */
interface Target {
    void request();
}

/**
 * 已有具体目标类，只提供普通功能
 * 已有的
 */
class ConcreteTarget implements Target {
    public void request() {
        System.out.println("原有实现类 具有 普通功能...");
    }
}

/**
 * 被适配者类，具有特殊功能、但不符合我们既有的标准接口(不能通过Target调用)
 * 已有的
 */
class Adaptee {
    public void specificRequest() {
        System.out.println("原有被适配类 具有 特殊功能...");
    }
}

/**
 * 新增适配者
 * 通过继承被适配者并且实现目标接口，使得可以通过Target调用具有特殊功能的被适配者类
 */
class ConcreteAdapter extends Adaptee implements Target {

    @Override
    public void request() {
        System.out.println("新增适配类，使目标接口可以调用被适配者类");
        specificRequest();
    }
}