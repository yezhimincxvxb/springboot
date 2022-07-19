package com.yzm.designmode.CreateMode创建型模式.Singleton单例模式;

public class EnumSingletonClient {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                SingletonG.INSTANCE.count();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                SingletonG.INSTANCE.count();
            }
        }, "t2");

        t1.start();
        t2.start();
        while (Thread.activeCount() > 2) Thread.yield();
        System.out.println(SingletonG.INSTANCE.getCount());
    }
}

/**
 * 枚举类
 *
 * JDK 版本：JDK1.5 起
 * 是否 Lazy 初始化：否
 * 是否多线程安全：是
 * 实现难度：易
 * 描述：它不仅能避免多线程同步问题，而且还自动支持序列化机制，防止反序列化重新创建新的对象，绝对防止多次实例化。
 * 不过，由于 JDK1.5 之后才加入 enum 特性，用这种方式写不免让人感觉生疏，在实际工作中，也很少用。
 * 可以防止通过反射和反序列化来重新创建新的对象
 */
enum SingletonG {
    INSTANCE;

    private int count;

    public int getCount() {
        return count;
    }

    public void count() {
        System.out.println(Thread.currentThread().getName() + "==> count：" + count);
        count++;
    }
}
