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
        Target target = new ConcreteAdapter();
        target.request_220V();
    }
}

/**
 * 目标接口
 */
interface Target {
    void request_220V();
}

/**
 * 被适配者
 */
class Adaptee {
    int v = 20;

    void request_20V() {
        System.out.println("支持电压(伏特)：" + v);
    }
}

/**
 * 适配者，继承被适配者并且实现目标接口
 * 起到适配、包装、转换的作用
 */
class ConcreteAdapter extends Adaptee implements Target {

    @Override
    public void request_220V() {
        //原来支持的
        request_20V();

        v = v * 11;
        System.out.println("经转换后可支持电压(伏特)：" + v);
    }
}