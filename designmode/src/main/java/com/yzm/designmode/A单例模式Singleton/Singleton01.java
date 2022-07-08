package com.yzm.designmode.A单例模式Singleton;

/**
 * 单例模式
 * Singleton 模式主要作用是保证在 Java 应用程序中，一个类 Class 只有一个实例存在。
 * 单例-饿汉式
 * 空间换时间，线程安全
 */
public class Singleton01 {

    //私有构造器
    private Singleton01() {}

    //类加载时，自行实例化
    //如果存在释放资源的情况下，就不能加final修饰了
    private static final Singleton01 instance = new Singleton01();

    public static Singleton01 getInstance() {
        return instance;
    }

}
