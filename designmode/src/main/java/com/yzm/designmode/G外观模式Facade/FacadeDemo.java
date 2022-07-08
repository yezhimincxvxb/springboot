package com.yzm.designmode.G外观模式Facade;

/**
 * 外观模式
 * Facade 模式的定义: 为子系统中的一组接口提供一个一致的界面.
 * 定义一个类(外观类)统一管理一组类
 */
public class FacadeDemo {
    public static void main(String[] args) {
        FacadeControl control = new FacadeControl(new SubSystemA_Light(), new SubSystemB_Television(), new SubSystemC_AirConditioner());
        control.on();
    }
}

class SubSystemA_Light {
    public void on() {
        System.out.println("打开了灯....");
    }

    public void off() {
        System.out.println("关闭了灯....");
    }
}

class SubSystemB_Television {
    public void on() {
        System.out.println("打开了电视....");
    }

    public void off() {
        System.out.println("关闭了电视....");
    }
}

class SubSystemC_AirConditioner {
    public void on() {
        System.out.println("打开了空调....");
    }

    public void off() {
        System.out.println("关闭了空调....");
    }
}

/**
 * 外观类——万能遥控器
 */
class FacadeControl {

    private SubSystemA_Light light;
    private SubSystemB_Television television;
    private SubSystemC_AirConditioner airConditioner;

    public FacadeControl(SubSystemA_Light light, SubSystemB_Television television, SubSystemC_AirConditioner airConditioner) {
        this.light = light;
        this.television = television;
        this.airConditioner = airConditioner;
    }

    public void on() {
        System.out.println("一键启动");
        light.on();
        television.on();
        airConditioner.on();
    }

    public void off() {
        System.out.println("一键关闭");
        light.off();
        television.off();
        airConditioner.off();
    }

}