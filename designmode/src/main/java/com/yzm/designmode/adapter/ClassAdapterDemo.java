package com.yzm.designmode.adapter;

/**
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
 * 已有目标接口
 *
 */
interface Target {
    void call();
}

/**
 * 已有 资源类
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