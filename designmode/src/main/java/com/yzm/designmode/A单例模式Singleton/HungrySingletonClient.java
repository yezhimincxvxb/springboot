package com.yzm.designmode.A单例模式Singleton;

/**
 * 单例模式
 * 主要作用是保证在 Java 应用程序中，一个类 Class 只有一个实例存在。
 */
public class HungrySingletonClient {
    public static void main(String[] args) {
//        test01();
        test02();
    }

    private static void test02() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                SingletonB.getInstance().count();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                SingletonB.getInstance().count();
            }
        }, "t2");

        t1.start();
        t2.start();
        while (Thread.activeCount() > 2) Thread.yield();
        System.out.println(SingletonB.getInstance().getCount());
    }

    private static void test01() {
        SingletonA instance = SingletonA.getInstance();
        SingletonA instance2 = SingletonA.getInstance();
        System.out.println(instance);
        System.out.println(instance2);
    }
}

/**
 * 单例-饿汉式
 * 加载单例类时开始创建对象，空间换时间，线程安全
 */
class SingletonA {

    //私有构造器
    private SingletonA() {
    }

    //类加载时，自行实例化
    //如果存在释放资源的情况下，就不能加final修饰了
    private static final SingletonA instance = new SingletonA();

    public static SingletonA getInstance() {
        return instance;
    }

}

/**
 * 单例状态化
 */
class SingletonB {

    private int count;

    //私有构造器
    private SingletonB() {
    }

    //类加载时，自行实例化
    //如果存在释放资源的情况下，就不能加final修饰了
    private static final SingletonB instance = new SingletonB();

    public static SingletonB getInstance() {
        return instance;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void count() {
        System.out.println(Thread.currentThread().getName() + "==> count：" + count);
        count++;
    }

}