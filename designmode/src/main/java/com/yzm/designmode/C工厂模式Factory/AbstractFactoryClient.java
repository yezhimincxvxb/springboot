package com.yzm.designmode.C工厂模式Factory;

/**
 * 抽象工厂模式
 */
public class AbstractFactoryClient {
    public static void main(String[] args) {
        AbstractFactory hw = new HuaWeiFactory();
        AbstractFactory az = new AnZhouFactory();

        hw.makePhone().make();
        hw.makeComputer().make();
        System.out.println("----------");
        az.makePhone().make();
        az.makeComputer().make();
    }

}

interface AbstractFactory {
    Phone makePhone();

    Computer makeComputer();
}

/**
 * 一个工厂生产多种类型产品
 */
class HuaWeiFactory implements AbstractFactory {
    @Override
    public Phone makePhone() { return new HuaWeiPhone(); }

    @Override
    public Computer makeComputer() { return new HuaWeiComputer(); }
}

class AnZhouFactory implements AbstractFactory {

    @Override
    public Phone makePhone() { return new AnZhouPhone(); }

    @Override
    public Computer makeComputer() { return new AnZhouComputer(); }
}

interface Phone {
    void make();
}

interface Computer {
    void make();
}

class HuaWeiPhone implements Phone {
    @Override
    public void make() { System.out.println("华为手机"); }
}

class AnZhouPhone implements Phone {
    @Override
    public void make() { System.out.println("安卓手机"); }
}

class HuaWeiComputer implements Computer {
    @Override
    public void make() { System.out.println("华为电脑"); }
}

class AnZhouComputer implements Computer {
    @Override
    public void make() { System.out.println("安卓电脑"); }
}