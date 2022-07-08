package com.yzm.designmode.E适配器模式Adapter;

/**
 * 对象适配器模式
 */
public class ObjectAdapterDemo {
    public static void main(String[] args) {
        Target2 target2 = new SourceAdapter2(new Source2());
        target2.call();
    }

}

interface Target2 {
    void call();
}

class Source2 {
    void collectClothes() {
        System.out.println("下雨了，收衣服啦！");
    }
}

class SourceAdapter2 implements Target2 {

    private final Source2 source2;

    public SourceAdapter2(Source2 source2) {
        this.source2 = source2;
    }

    @Override
    public void call() {
        System.out.println("打电话给某人：");
        source2.collectClothes();
    }
}