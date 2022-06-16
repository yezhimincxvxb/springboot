package com.yzm.thread.keyword;

/**
 * 单例模式
 */
public class InstanceDemo {
    private static volatile InstanceDemo instance;

    private InstanceDemo() {
    }

    public static InstanceDemo getInstance() {
        if (instance == null) {// 第一次检查
            synchronized (InstanceDemo.class) { // 加锁
                if (instance == null) { // 第二次检查
                    instance = new InstanceDemo(); // 问题的根源处在这里
                }
            }
        }
        return instance;
    }

    /**
     * 创建了一个对象，分为三个步骤(正常顺序)
     * memory = alloct();       //1：分配对象的内存空间
     * ctorinstance(memory);    //2：初始化对象
     * instance = memory;       //3: 设置instance指向刚分配的内存地址
     *
     * 重排序
     * memory = alloct();       //1：分配对象的内存空间
     * instance = memory;       //3: 设置instance指向刚分配的内存地址 (此时instance不为null但对象还未初始化)
     * ctorinstance(memory);    //2：初始化对象
     */

}
