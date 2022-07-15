package com.yzm.designmode.E适配器模式Adapter;

/**
 * 对象适配器模式
 */
public class ObjectAdapterClient {
    public static void main(String[] args) {
        Target2 target2 = new ConcreteAdapter2(new Adaptee2());
        target2.request_220V();
    }

}

interface Target2 {
    void request_220V();
}
/**
 * 被适配者
 */
class Adaptee2 {
    int v = 20;

    void request_20V() {
        System.out.println("支持电压(伏特)：" + v);
    }
}

class ConcreteAdapter2 implements Target2 {

    private final Adaptee2 adaptee2;

    public ConcreteAdapter2(Adaptee2 adaptee2) {
        this.adaptee2 = adaptee2;
    }

    @Override
    public void request_220V() {
        //原来支持的
        adaptee2.request_20V();

        System.out.println("经转换后可支持电压(伏特)：" + adaptee2.v * 11);
    }
}