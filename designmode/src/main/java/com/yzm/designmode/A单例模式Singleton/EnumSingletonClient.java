package com.yzm.designmode.A单例模式Singleton;

/**
 * 单例-枚举类
 * 可以防止通过反射和反序列化来重新创建新的对象
 */
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
