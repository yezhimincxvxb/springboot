package com.yzm.designmode.CreateMode创建型模式.Singleton单例模式;


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
 * 懒汉式，非线程安全
 *
 * 是否 Lazy 初始化：是
 * 是否多线程安全：否
 * 实现难度：易
 * 描述：这种方式是最基本的实现方式，这种实现最大的问题就是不支持多线程。因为没有加锁 synchronized，所以严格意义上它并不算单例模式。
 * 这种方式 lazy loading 很明显，不要求线程安全，在多线程不能正常工作。
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
 * 懒汉式，线程安全
 *
 * 是否 Lazy 初始化：是
 * 是否多线程安全：是
 * 实现难度：易
 * 描述：这种方式具备很好的 lazy loading，能够在多线程中很好的工作，但是，效率很低，99% 情况下不需要同步。
 * 优点：第一次调用才初始化，避免内存浪费。
 * 缺点：必须加锁 synchronized 才能保证单例，但加锁会影响效率。
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
 * 双重校验锁(DCL) + volatile
 *
 * JDK 版本：JDK1.5 起
 * 是否 Lazy 初始化：是
 * 是否多线程安全：是
 * 实现难度：较复杂
 * 描述：这种方式采用双锁机制，安全且在多线程情况下能保持高性能。
 *
 * volatile的作用：禁止指令重排序，创建对象是分成3个步骤的，1-分配内存空间，2-实例对象初始化，3-对象引用指向内存地址
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
 * 静态内部类
 *
 * 是否 Lazy 初始化：是
 * 是否多线程安全：是
 * 实现难度：一般
 * 描述：这种方式能达到双检锁方式一样的功效，但实现更简单。对静态域使用延迟初始化，应使用这种方式而不是双检锁方式。这种方式只适用于静态域的情况，双检锁方式可在实例域需要延迟初始化时使用。
 * 只有通过显式调用 getInstance 方法时，才会显式装载 SingletonHolder 内部类，从而实例化 instance。
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