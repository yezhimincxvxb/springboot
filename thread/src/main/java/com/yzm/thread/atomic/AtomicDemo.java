package com.yzm.thread.atomic;

import java.util.concurrent.atomic.*;

/**
 * 原子操作
 */
public class AtomicDemo {

    public static void main(String[] args) {
//        demo01();
//        demo02();
        demo03();
//        demo04();
//        demo05();
//        demo06();
//        demo07();
    }

    private static final AtomicInteger ai = new AtomicInteger(0);


    public static int a = 0;
    public static AtomicInteger b = new AtomicInteger();

    private static void demo01() {
        Thread t1 = new Thread(AtomicDemo::method01, "t1");
        Thread t2 = new Thread(AtomicDemo::method01, "t2");
        Thread t3 = new Thread(AtomicDemo::method01, "t3");
        t1.start();
        t2.start();
        t3.start();

        while (Thread.activeCount() > 1) Thread.yield();
        System.out.println(a);
    }

    private static void method01() {
        String thName = Thread.currentThread().getName();
        System.err.println("线程：" + thName + " >> " + a);
        for (int i = 0; i < 1000; i++) {
            a++;
        }
        System.out.println("线程：" + thName + " >> " + a);
    }

    private static void demo02() {
        Thread t1 = new Thread(AtomicDemo::method02, "t1");
        Thread t2 = new Thread(AtomicDemo::method02, "t2");
        Thread t3 = new Thread(AtomicDemo::method02, "t3");
        t1.start();
        t2.start();
        t3.start();

        while (Thread.activeCount() > 1) Thread.yield();
        System.out.println(b.get());
    }

    private static void method02() {
        String thName = Thread.currentThread().getName();
        System.err.println("线程：" + thName + " >> " + b.get());
        for (int i = 0; i < 1000; i++) {
            b.incrementAndGet();
        }
        System.out.println("线程：" + thName + " >> " + b.get());
    }

    //=========================================================================

    /**
     * 原子更新引用类型
     * AtomicReference：原子更新引用类型。
     * AtomicMarkableReference：原子更新带有标记位的引用类型
     * AtomicStampedReference：原子更新带有版本号的引用类型。该类将整数值与引用关联起来，可用于原子的更新数据和数据的版本号，可以解决使用 CAS 进行原子更新时可能出现的 ABA 问题。
     */
    private static void demo03() {
        AtomicReference<String> ars = new AtomicReference<>("字符串");
        System.out.println(ars.get());
        ars.compareAndSet("预期值", "更新值");
        System.out.println(ars.get());
        //或者实体类、数组等
        //AtomicReference<AtomicDemo> ars2 = new AtomicReference<>(new AtomicDemo());
    }

    private static void demo04() {
        AtomicMarkableReference<Integer> amsi = new AtomicMarkableReference<>(0, false);
        System.out.println("更新前：" + amsi.isMarked());
        System.out.println("更新前：" + amsi.getReference());

        //未被标记，进行标志
        if (!amsi.isMarked() && amsi.attemptMark(0, true)) {
            //更新
            if (amsi.compareAndSet(0, 1, true, false)) {
                System.out.println("更新成功");
            } else {
                System.out.println("更新失败");
            }
        }

        System.out.println("更新后：" + amsi.isMarked());
        System.out.println("更新后：" + amsi.getReference());
    }

    private static void demo05() {
        AtomicStampedReference<String> asrs = new AtomicStampedReference<>("初始值", 1);
        System.out.println("更新前：" + asrs.getStamp());
        System.out.println("更新前：" + asrs.getReference());

        //更新
        if (asrs.compareAndSet("预期值=初始值", "更新值", 1, asrs.getStamp() + 1)) {
            System.out.println("更新成功");
        } else {
            System.out.println("更新失败");
        }

        System.out.println("更新后：" + asrs.getStamp());
        System.out.println("更新后：" + asrs.getReference());
    }

    //=========================================================================

    /**
     * 原子更新数组
     * AtomicIntegerArray：原子更新整型数组里的元素。
     * AtomicLongArray：原子更新长整型数组里的元素。
     * AtomicReferenceArray：原子更新引用类型数组里的元素。。
     */
    private static void demo06() {
        int[] is = {1, 2, 3, 4, 5};
        AtomicIntegerArray aia = new AtomicIntegerArray(is);

        System.out.println(aia);
        System.out.println(aia.getAndAdd(1, 10));
        System.out.println(aia);
    }


    //=========================================================================

    private volatile int age = 18;

    /**
     * 原子更新属性
     * AtomicIntegerFieldUpdater：原子更新整型的字段的更新器。
     * AtomicLongFieldUpdater：原子更新长整型字段的更新器。
     * AtomicReferenceFieldUpdater：原子更新带有版本号的引用类型。该类将整数值与引用关联起来，可用于原子的更新数据和数据的版本号，可以解决使用 CAS 进行原子更新时可能出现的 ABA 问题。
     */
    private static void demo07() {
        AtomicDemo demo = new AtomicDemo();
        AtomicIntegerFieldUpdater<AtomicDemo> aifu = AtomicIntegerFieldUpdater.newUpdater(AtomicDemo.class, "age");

        System.out.println(demo.age);
        System.out.println(aifu.get(demo));

        System.out.println(aifu.getAndIncrement(demo));
        System.out.println(demo.age);
        System.out.println(aifu.get(demo));
    }

}
