package com.yzm.designmode.B工厂模式Factory;

/**
 * 工厂模式：
 * 实现了创建者和调用者的分离。
 * 将实例化对象的代码提取出来，放到一个类(工厂类)中统一管理和维护，达到和主项目的依赖关系的解耦。从而提高项目的扩展和维护性。
 *
 * 简单工厂模式
 */
public class SimpleFactoryClient {

    public static void main(String[] args) {
        Produce a = SimpleFactory.create("A");
        Produce b = SimpleFactory.create("B");
        Produce c = SimpleFactory.create("C");

        a.make();
        b.make();
        c.make();
    }

}

/**
 * 根据传入参数返回不同类型对象引用
 */
class SimpleFactory {
    public static Produce create(String name) {
        switch (name) {
            case "A":
                return new ClothingProduce();
            case "B":
                return new PantsProduce();
            case "C":
                return new ShoeProduce();
            default:
                return null;
        }
    }
}

interface Produce {
    void make();
}

class ClothingProduce implements Produce {

    @Override
    public void make() {
        System.out.println("服装产品");
    }
}

class PantsProduce implements Produce {

    @Override
    public void make() {
        System.out.println("裤子产品");
    }
}

class ShoeProduce implements Produce {

    @Override
    public void make() {
        System.out.println("鞋类产品");
    }
}