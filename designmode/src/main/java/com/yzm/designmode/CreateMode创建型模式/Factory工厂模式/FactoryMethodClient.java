package com.yzm.designmode.CreateMode创建型模式.Factory工厂模式;

/**
 * 工厂方法模式
 * 定义一个抽象工厂，其定义了产品的生产接口，但不负责具体的产品，将生产任务交给不同的派生类工厂。这样不用通过指定类型来创建对象了。
 *
 * 简单工厂和工厂方法模式的不同在于前者生成产生产品的行为封装在一个方法中，根据参数的类型进行实例化，同时不存在抽象接口。
 * 而后者则增加了抽象接口，通过实现不同的工厂方法来创建不同的产品，一个方法通常对应一个产品，
 * 这种方式相较于前者扩展性更高，在需求增加时完全符合开闭原则和依赖倒置原则
 */
public class FactoryMethodClient {

    public static void main(String[] args) {
        FactoryMethod appleFactory = new AppleFactory();
        FactoryMethod bananaFactory = new BananaFactory();
        FactoryMethod peachFactory = new PeachFactory();

        appleFactory.make().name();
        bananaFactory.make().name();
        peachFactory.make().name();
    }

}

/**
 * 步骤 1
 * 定义抽象产品接口
 */
interface Fruit {
    void name();
}

/**
 * 步骤 2
 * 实现接口
 */
class Apple implements Fruit {

    @Override
    public void name() { System.out.println("苹果"); }
}

class Banana implements Fruit {

    @Override
    public void name() { System.out.println("香蕉"); }
}

class Peach implements Fruit {

    @Override
    public void name() { System.out.println("桃子"); }
}

/**
 * 步骤 3
 * 定义抽象工厂接口，规范子类工厂
 */
interface FactoryMethod {
    Fruit make();
}

/**
 * 步骤 4
 * 由工厂子类负责生产具体的产品
 */
class AppleFactory implements FactoryMethod {

    @Override
    public Fruit make() { return new Apple(); }
}

class BananaFactory implements FactoryMethod {

    @Override
    public Fruit make() { return new Banana(); }
}

class PeachFactory implements FactoryMethod {

    @Override
    public Fruit make() { return new Peach(); }
}


