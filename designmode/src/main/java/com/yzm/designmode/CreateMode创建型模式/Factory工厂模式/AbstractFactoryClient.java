package com.yzm.designmode.CreateMode创建型模式.Factory工厂模式;

/**
 * 抽象工厂模式（Abstract Factory Pattern）是围绕一个超级工厂创建其他工厂。该超级工厂又称为其他工厂的工厂。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。
 * 在抽象工厂模式中，接口是负责创建一个相关对象的工厂，不需要显式指定它们的类。每个生成的工厂都能按照工厂模式提供对象。
 * <p>
 * 意图：提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们具体的类。
 * 主要解决：主要解决接口选择的问题。
 * 何时使用：系统的产品有多于一个的产品族，而系统只消费其中某一族的产品。
 * 如何解决：在一个产品族里面，定义多个产品。
 * 关键代码：在一个工厂里聚合多个同类产品。
 * 应用实例：一系列产品家族
 * 优点：当一个产品族中的多个对象被设计成一起工作时，它能保证客户端始终只使用同一个产品族中的对象。
 * 缺点：产品族扩展非常困难，要增加一个系列的某一产品，既要在抽象的 Creator 里加代码，又要在具体的里面加代码。
 * 使用场景： 1、QQ 换皮肤，一整套一起换。 2、生成不同操作系统的程序。
 * 注意事项：产品族难扩展，产品等级易扩展。
 */
public class AbstractFactoryClient {
    public static void main(String[] args) {
        AbstractFactory hw = new HuaWeiFactory();
        AbstractFactory az = new AndroidFactory();
        AbstractFactory i = new IFactory();

        hw.makePhone().make();
        hw.makeComputer().make();
        System.out.println("----------");

        az.makePhone().make();
        az.makeComputer().make();
        System.out.println("----------");

        i.makePhone().make();
    }

}

/**
 * 步骤 1
 * 手机系列产品
 */
interface Phone {
    void make();
}

class HuaWeiPhone implements Phone {
    @Override
    public void make() {
        System.out.println("华为手机");
    }
}

class AndroidPhone implements Phone {
    @Override
    public void make() {
        System.out.println("安卓手机");
    }
}

class IPhone implements Phone {
    @Override
    public void make() {
        System.out.println("苹果手机");
    }
}

/**
 * 步骤 2
 * 电脑系列产品
 */
interface Computer {
    void make();
}

class HuaWeiComputer implements Computer {
    @Override
    public void make() {
        System.out.println("华为电脑");
    }
}

class AndroidComputer implements Computer {
    @Override
    public void make() {
        System.out.println("安卓电脑");
    }
}


/**
 * 步骤 3
 * 抽象工厂，规范工厂类
 */
interface AbstractFactory {
    Phone makePhone();

    Computer makeComputer();
}

/**
 * 步骤 4
 * 具体工厂类
 */
class HuaWeiFactory implements AbstractFactory {
    @Override
    public Phone makePhone() {
        return new HuaWeiPhone();
    }

    @Override
    public Computer makeComputer() {
        return new HuaWeiComputer();
    }
}

class AndroidFactory implements AbstractFactory {

    @Override
    public Phone makePhone() {
        return new AndroidPhone();
    }

    @Override
    public Computer makeComputer() {
        return new AndroidComputer();
    }
}

/**
 * 增加苹果工厂，但只生产苹果手机
 */
class IFactory implements AbstractFactory {

    @Override
    public Phone makePhone() {
        return new IPhone();
    }

    @Override
    public Computer makeComputer() {
        return null;
    }
}