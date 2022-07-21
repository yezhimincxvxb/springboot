package com.yzm.designmode.StructuralPattern结构型模式.Adapter适配器模式;

/**
 * 对象适配器模式
 */
public class ObjectAdapterClient {
    public static void main(String[] args) {
        //原有功能
        Computer computer = new ConcreteComputer();
        SDCard sd = new SDCardImpl();
        computer.readSD(sd);
        System.out.println();

        //适配功能
        TFCard tf = new TFCardImpl();
        SDCard adapt = new SDAdapterTF(tf);
        computer.readSD(adapt);
    }

}

/**
 * 步骤 1 已有功能不可修改
 * SD卡的接口，模拟计算机读取SD卡
 */
interface SDCard {
    //读取SD卡方法
    void readSD();

    //写入SD卡功能
    void writeSD(String msg);
}

class SDCardImpl implements SDCard {
    @Override
    public void readSD() {
        System.out.println("读取SD卡");
    }

    @Override
    public void writeSD(String msg) {
        System.out.println("写入SD卡");
    }
}

/**
 * 步骤 2 已有功能不可修改
 * 计算机接口，计算机提供读取SD卡方法：
 */
interface Computer {
    void readSD(SDCard sdCard);
}

class ConcreteComputer implements Computer {
    @Override
    public void readSD(SDCard sdCard) {
        sdCard.readSD();
    }
}

/**
 * 步骤 3 已有功能不可修改 被适配角色
 * 读取TF卡
 */
interface TFCard {
    void readTF();

    void writeTF(String msg);
}

class TFCardImpl implements TFCard {
    @Override
    public void readTF() {
        System.out.println("读取TF卡");
    }

    @Override
    public void writeTF(String msg) {
        System.out.println("写入TF卡");
    }
}

/**
 * 步骤 4 适配器角色
 * 已知计算机可以读取SD卡，在不改变计算机代码的情况下，要使计算机可以读取TF卡，那就需要将SD卡和TF卡进行兼容
 */
class SDAdapterTF implements SDCard {
    private final TFCard tfCard;

    public SDAdapterTF(TFCard tfCard) {
        this.tfCard = tfCard;
    }

    @Override
    public void readSD() {
        System.out.println("适配器：兼容SD卡和TF卡 ");
        tfCard.readTF();
    }

    @Override
    public void writeSD(String msg) {
        System.out.println("适配器：兼容SD卡和TF卡 ");
        tfCard.writeTF(msg);
    }
}
