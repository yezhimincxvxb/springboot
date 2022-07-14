package com.yzm.designmode.A单例模式Singleton;


public class LazySingletonClient {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println("instance = " + SingletonF.getInstance());
            }).start();
        }
    }

}

/**
 * 单例-懒汉式
 * 第一次调用时初始化对象
 * 时间换空间，非线程安全，并发环境有可能创建多个实例
 */
class SingletonC {

    //私有构造器
    private SingletonC() {
    }

    //初始不创建实例，第一次调用时才创建
    private static SingletonC instance;

    public static SingletonC getInstance() {
        if (instance == null) {
            instance = new SingletonC();
        }
        return instance;
    }

}

/**
 * 改造一：
 * 使用synchronized实现同步方法(粗粒度)
 * 线程安全，但影响性能
 */
class SingletonD {

    private SingletonD() {
    }

    private static SingletonD instance;

    public static synchronized SingletonD getInstance() {
        if (instance == null) {
            instance = new SingletonD();
        }
        return instance;
    }
}

/**
 * 改造二：双重检查锁定 + volatile
 * synchronized同步代码块(细粒度)
 * volatile 禁止指令重排序，创建对象是分成3个步骤的，1-分配内存空间，2-实例对象初始化，3-对象引用指向内存地址
 * 线程安全，性能较小影响
 */
class SingletonE {

    private SingletonE() {
    }

    private static volatile SingletonE instance;

    public static SingletonE getInstance() {
        if (instance == null) {
            synchronized (SingletonE.class) {
                if (instance == null) {
                    instance = new SingletonE();
                }
            }
        }
        return instance;
    }
}

/**
 * 改造三：
 * 静态内部类，没有性能影响
 * 静态内部类，当外部类被加载的时候，并不会创建内部类实例对象。
 * 只有当调用 getInstance() 方法时，内部类才会被加载，这个时候才会创建 instance。
 */
class SingletonF {

    private SingletonF() {
    }

    private static class LazyHolder {
        private static final SingletonF INSTANCE = new SingletonF();
    }

    public static SingletonF getInstance() {
        return LazyHolder.INSTANCE;
    }
}