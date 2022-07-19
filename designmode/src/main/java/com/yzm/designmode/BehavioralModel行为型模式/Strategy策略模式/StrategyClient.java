package com.yzm.designmode.BehavioralModel行为型模式.Strategy策略模式;

/**
 * 策略模式
 * 定义一系列算法，将每个算法封装到具有公共接口的一系列策略类中，从而使它们可以相互替换，并让算法可以在不影响到客户端的情况下发生变化。
 * <p>
 * 作用
 * 策略模式仅仅封装算法（包括添加 & 删除），但策略模式并不决定在何时使用何种算法，算法的选择由客户端来决定
 * 将算法的责任和本身进行解耦
 * 对算法进行封装，将算法的责任和算法本身分割开，委派给不同的对象管理。
 */
public class StrategyClient {
    public static void main(String[] args) {
        System.out.println("-------组装宝马-------");
        Car baoma = new BaoMaCar();
        baoma.electrical();
        baoma.low();
        baoma.frame(new RedStrategy());
        baoma.engine(new FuelStrategy());

        System.out.println("-------组装奔驰-------");
        Car benchi = new BenChiCar();
        benchi.electrical();
        benchi.low();
        benchi.frame(new WhiteStrategy());
        benchi.engine(new NewEnergyStrategy());

    }
}

/**
 * 汽车超类
 */
abstract class Car {

    void electrical() {
        System.out.println("组装电器设备");
    }

    void low() {
        System.out.println("组装低盘");
    }

    //车身颜色可选
    abstract void frame(FrameStrategy strategy);

    //发动机类型可选
    abstract void engine(EngineStrategy strategy);
}

class BaoMaCar extends Car {

    @Override
    void frame(FrameStrategy strategy) {
        System.out.println("组装" + strategy.getColor() + "车身");
    }

    @Override
    void engine(EngineStrategy strategy) {
        System.out.println("组装" + strategy.getEngineName() + "发动机");
    }
}

class BenChiCar extends Car {

    @Override
    void frame(FrameStrategy strategy) {
        System.out.println("组装" + strategy.getColor() + "车身");
    }

    @Override
    void engine(EngineStrategy strategy) {
        System.out.println("组装" + strategy.getEngineName() + "发动机");
    }
}

/**
 * 车身策略
 */
interface FrameStrategy {
    String getColor();
}

class RedStrategy implements FrameStrategy {

    @Override
    public String getColor() {
        return "红色";
    }
}

class WhiteStrategy implements FrameStrategy {

    @Override
    public String getColor() {
        return "白色";
    }

}

class BlackStrategy implements FrameStrategy {

    @Override
    public String getColor() {
        return "黑色";
    }

}

/**
 * 发动机策略
 */
interface EngineStrategy {
    String getEngineName();
}


class FuelStrategy implements EngineStrategy {

    @Override
    public String getEngineName() {
        return "燃油";
    }
}

class NewEnergyStrategy implements EngineStrategy {

    @Override
    public String getEngineName() {
        return "新能源";
    }
}