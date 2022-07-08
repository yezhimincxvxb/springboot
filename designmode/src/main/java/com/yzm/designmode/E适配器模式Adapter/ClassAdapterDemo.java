package com.yzm.designmode.E适配器模式Adapter;

/**
 * 适配器模式定义:
 * 将两个不兼容的类纠合在一起使用，属于结构型模式,需要有 Adaptee(被适配者)和 Adaptor(适配器)两个身份
 * 类的适配器模式
 * 把一个类的接口变换成客户端所期待的另一种接口，从而使原本接口不匹配而无法一起工作的两个类能够在一起工作。
 */
public class ClassAdapterDemo {
    public static void main(String[] args) {
        Target target = new SourceAdapter();
        target.call();
    }
}

/**
 * 已有目标接口，对接口功能进行更改，但不影响之前调用者的使用
 *
 */
interface Target {
    void call();
}

/**
 * 已有资源类，
 */
class Source {
    void collectClothes() {
        System.out.println("下雨了，收衣服啦！");
    }
}

class SourceAdapter extends Source implements Target {
    @Override
    public void call() {
        System.out.println("打电话给某人：");
        this.collectClothes();
    }
}