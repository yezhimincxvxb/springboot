package com.yzm.designmode.factory;

/**
 * 抽象工厂模式
 */
public class AbstractFactoryDemo {
    public static void main(String[] args) {
        AbstractFactory hw = new HuaWeiFactory();
        AbstractFactory az = new AnZhouFactory();
        hw.makePhone().doing();
        hw.makeComputer().doing();
        System.out.println("----------");
        az.makePhone().doing();
        az.makeComputer().doing();
    }

}

interface AbstractFactory {
    PhoneB makePhone();
    Computer makeComputer();
}

class HuaWeiFactory implements AbstractFactory{
    @Override
    public PhoneB makePhone() {
        return new HuaWeiPhone();
    }

    @Override
    public Computer makeComputer() {
        return new HuaWeiComputer();
    }
}

class AnZhouFactory implements AbstractFactory{

    @Override
    public PhoneB makePhone() {
        return new AnZhouPhone();
    }

    @Override
    public Computer makeComputer() {
        return new AnZhouComputer();
    }
}

interface PhoneB {
    void doing();
}

interface Computer {
    void doing();
}

class HuaWeiPhone implements PhoneB{
    @Override
    public void doing() {
        System.out.println("华为手机");
    }
}
class AnZhouPhone implements PhoneB{
    @Override
    public void doing() {
        System.out.println("安卓手机");
    }
}
class HuaWeiComputer implements Computer{
    @Override
    public void doing() {
        System.out.println("华为电脑");
    }
}
class AnZhouComputer implements Computer{
    @Override
    public void doing() {
        System.out.println("安卓电脑");
    }
}