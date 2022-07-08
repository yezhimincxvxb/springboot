package com.yzm.designmode.B工厂模式Factory;

/**
 * 工厂方法模式
 */
public class FactoryMethodDemo {

    public static void main(String[] args) {
        FactoryMethod xf = new XiaoMiFactory();
        FactoryMethod af = new AppleFactory();
        xf.makePhone().doing();
        af.makePhone().doing();
    }

}
interface FactoryMethod{
    Phone makePhone();
}

class XiaoMiFactory implements FactoryMethod{

    @Override
    public Phone makePhone() {
        return new MiPhone();
    }
}

class AppleFactory implements FactoryMethod{

    @Override
    public Phone makePhone() {
        return new IPhone();
    }
}

interface Phone {
  void doing();
}

class MiPhone implements Phone{

    @Override
    public void doing() {
        System.out.println("小米手机");
    }
}

class IPhone implements Phone{

    @Override
    public void doing() {
        System.out.println("苹果手机");
    }
}