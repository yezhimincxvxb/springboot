package com.yzm.designmode.factory;

/**
 * 简单工厂模式
 */
public class SampleFactory {

    public static Sample create(String name) {
        if ("A".equals(name)) {
            return new SampleA();
        } else if ("B".equals(name)) {
            return new SampleB();
        }
        return null;
    }

    public static void main(String[] args) {
        Sample a = SampleFactory.create("A");
        Sample b = SampleFactory.create("B");
        a.doing();
        b.doing();
    }

}

interface Sample {
    void doing();
}

class SampleA implements Sample{

    @Override
    public void doing() {
        System.out.println("SampleA");
    }
}

class SampleB implements Sample{

    @Override
    public void doing() {
        System.out.println("SampleB");
    }
}