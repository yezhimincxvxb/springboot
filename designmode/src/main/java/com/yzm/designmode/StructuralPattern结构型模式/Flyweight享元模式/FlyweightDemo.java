package com.yzm.designmode.StructuralPattern结构型模式.Flyweight享元模式;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 享元模式（Flyweight pattern）又叫轻量级模式，是对象池的一种标签。类似线程池，线程池可以避免不停的创建和销毁对象，消耗性能。
 * 享元模式可以减少对象数量，其宗旨是共享细粒度对象，将多个对同一对象的访问集中起来，属于结构型设计模式
 * <p>
 * 主要的3个角色
 * 1.抽象享元角色（IFlyweight）: 享元对象抽象基类或接口，同时定义出对象的外部状态和内部状态的接口或实现。
 * 2.具体享元角色（ConcreteFlyweight）:实现抽象角色定义的业务。该角色的内部状态处理应该与环境无关，不会出现一个操作改变内部状态，同时修改了外部状态的情况
 * 3.非享元角色（Unsharable Flyweight)：是不可以共享的外部状态，它以参数的形式注入具体享元的相关方法中。
 * 4.享元工厂（FlyweightFactory）:负责创建和管理享元对象、维护一个享元池(Flyweight Pool)
 * <p>
 * 在享元模式中可以共享的相同内容称为 内部状态(Intrinsic State)，而那些需要外部环境来设置的不能共享的内容称为 外部状态(Extrinsic State)，
 * 其中外部状态和内部状态是相互独立的，外部状态的变化不会引起内部状态的变化。由于区分了内部状态和外部状态，因此可以通过设置不同的外部状态使得相同的对象可以具有一些不同的特征，
 * 而相同的内部状态是可以共享的。也就是说，享元模式的本质是分离与共享 ： 分离变与不变，并且共享不变。把一个对象的状态分成内部状态和外部状态，
 * 内部状态即是不变的，外部状态是变化的；然后通过共享不变的部分，达到减少对象数量并节约内存的目的。
 * <p>
 * JDK中应用的享元模式：
 * String字符串常量池
 * Integer-128到127的缓存
 */
public class FlyweightDemo {
    public static void main(String[] args) {
        IFlyweight flyweight1 = FlyweightFactory.getFlyweight("aa");
        IFlyweight flyweight2 = FlyweightFactory.getFlyweight("aa");
        IFlyweight flyweight3 = FlyweightFactory.getFlyweight("cc");
        flyweight1.operation(new UnsharedConcreteFlyweight("www"));
        flyweight2.operation(new UnsharedConcreteFlyweight("xxx"));
        flyweight3.operation(new UnsharedConcreteFlyweight("yyy"));
    }
}

/**
 * 抽象享元角色
 */
interface IFlyweight {

    void operation(UnsharedConcreteFlyweight unshared);

}

/**
 * 具体享元角色
 */
class ConcreteFlyweight implements IFlyweight {

    private final String intrinsicState;

    public ConcreteFlyweight(String intrinsicState) {
        this.intrinsicState = intrinsicState;
    }

    @Override
    public void operation(UnsharedConcreteFlyweight unshared) {
        System.out.println("object address: " + System.identityHashCode(this));
        System.out.println("内部状态: " + intrinsicState);
        System.out.println("外部状态: " + unshared.getExtrinsicState());
    }
}

/**
 * 非享元角色
 */
class UnsharedConcreteFlyweight {
    private String extrinsicState;

    public UnsharedConcreteFlyweight(String extrinsicState) {
        this.extrinsicState = extrinsicState;
    }

    public String getExtrinsicState() {
        return extrinsicState;
    }

    public void setExtrinsicState(String extrinsicState) {
        this.extrinsicState = extrinsicState;
    }
}

/**
 * 享元工厂
 */
class FlyweightFactory {

    public static Map<String, IFlyweight> pool = new ConcurrentHashMap<>();

    public static IFlyweight getFlyweight(String intrinsicState) {
        if (!pool.containsKey(intrinsicState)) {
            IFlyweight flyweight = new ConcreteFlyweight(intrinsicState);
            pool.put(intrinsicState, flyweight);
        }
        return pool.get(intrinsicState);
    }
}



