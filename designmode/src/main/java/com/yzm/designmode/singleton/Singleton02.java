package com.yzm.designmode.singleton;

/**
 * 单例-懒汉式
 * 时间换空间，非线程安全，并发环境有可能创建多个实例
 */
public class Singleton02 {

    //私有构造器
    private Singleton02() {}

    //初始不创建实例，第一次调用时才创建
    private static Singleton02 instance;

    public static Singleton02 getInstance() {
        if (instance == null) {
            instance = new Singleton02();
        }
        return instance;
    }

    //------------------------------------------------------------------
    //改造一，同步方法，粗粒度，性能有影响
    public static synchronized Singleton02 getInstance2() {
        if (instance == null) {
            instance = new Singleton02();
        }
        return instance;
    }

    //------------------------------------------------------------------
    //改造二，双重检查锁定 + volatile ，细粒度，性能有影响
    //volatile 禁止指令重排序，创建对象是分成3个步骤的，1-分配内存空间，2-实例对象初始化，3-对象引用指向内存地址
    public static Singleton02 getInstance3() {
        if (instance == null) {
            synchronized (Singleton02.class) {
                if (instance == null) {
                    instance = new Singleton02();
                }
            }
        }
        return instance;
    }

    //------------------------------------------------------------------
    //改造三，静态内部类，没有性能影响
    private static class LazyHolder {
        private static final Singleton02 INSTANCE = new Singleton02();
    }

    public static Singleton02 getInstance4() {
        return LazyHolder.INSTANCE;
    }

}
