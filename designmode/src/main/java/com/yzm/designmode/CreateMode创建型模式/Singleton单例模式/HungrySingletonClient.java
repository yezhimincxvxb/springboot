package com.yzm.designmode.CreateMode创建型模式.Singleton单例模式;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 单例模式（Singleton Pattern）是 Java 中最简单的设计模式之一。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。
 * 这种模式涉及到一个单一的类，该类负责创建自己的对象，同时确保只有单个对象被创建。这个类提供了一种访问其唯一的对象的方式，可以直接访问，不需要实例化该类的对象。
 * <p>
 * 注意：
 * 1、单例类只能有一个实例。
 * 2、单例类必须自己创建自己的唯一实例。
 * 3、单例类必须给所有其他对象提供这一实例。
 * <p>
 * 意图：保证一个类仅有一个实例，并提供一个访问它的全局访问点。
 * 主要解决：一个全局使用的类频繁地创建与销毁。
 * 何时使用：当您想控制实例数目，节省系统资源的时候。
 * 如何解决：判断系统是否已经有这个单例，如果有则返回，如果没有则创建。
 * 关键代码：构造函数是私有的。
 * 应用实例：
 * 1、一个班级只有一个班主任。
 * 2、Windows 是多进程多线程的，在操作一个文件的时候，就不可避免地出现多个进程或线程同时操作一个文件的现象，所以所有文件的处理必须通过唯一的实例来进行。
 * 3、一些设备管理器常常设计为单例模式，比如一个电脑有两台打印机，在输出的时候就要处理不能两台打印机打印同一个文件。
 * 优点：
 * 1、在内存里只有一个实例，减少了内存的开销，尤其是频繁的创建和销毁实例（比如管理学院首页页面缓存）。
 * 2、避免对资源的多重占用（比如写文件操作）。
 * 缺点：没有接口，不能继承，与单一职责原则冲突，一个类应该只关心内部逻辑，而不关心外面怎么样来实例化。
 * 使用场景：
 * 1、要求生产唯一序列号。
 * 2、WEB 中的计数器，不用每次刷新都在数据库里加一次，用单例先缓存起来。
 * 3、创建的一个对象需要消耗的资源过多，比如 I/O 与数据库的连接等。
 * 注意事项：getInstance() 方法中需要使用同步锁 synchronized (Singleton.class) 防止多线程同时进入造成 instance 被多次实例化。
 */
public class HungrySingletonClient {
    public static void main(String[] args) {
//        test01();
//        test02();
        test03();
//        test04();
    }

    private static void test04() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                SingletonB.getInstance().count();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                SingletonB.getInstance().count();
            }
        }, "t2");

        t1.start();
        t2.start();
        while (Thread.activeCount() > 2) Thread.yield();
        System.out.println(SingletonB.getInstance().getCount());
    }

    private static void test03() {
        try {
            SingletonAAA s1 = SingletonAAA.getInstance();
            SingletonAAA s2 = SingletonAAA.getInstance();

            //序列化
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(s1);

            //反序列化
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            SingletonAAA s3 = (SingletonAAA) ois.readObject();

            System.out.println(s1);
            System.out.println(s2);
            System.out.println(s3);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //通过反射可以获取多个实例对象
    private static void test02() {
        try {
            Class clazz = Class.forName("com.yzm.designmode.CreateMode创建型模式.Singleton单例模式.SingletonAA");
            Constructor c = clazz.getDeclaredConstructor(null);
            c.setAccessible(true);

            SingletonAA s1 = (SingletonAA) c.newInstance();
            SingletonAA s2 = (SingletonAA) c.newInstance();
            //通过反射，得到的两个不同对象
            System.out.println(s1);
            System.out.println(s2);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

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
 * <p>
 * 是否 Lazy 初始化：否
 * 是否多线程安全：是
 * 实现难度：易
 * 描述：这种方式比较常用，但容易产生垃圾对象。
 * 优点：没有加锁，执行效率会提高。
 * 缺点：类加载时就初始化，浪费内存。
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
 * 防反射
 */
class SingletonAA {

    //私有构造器
    private SingletonAA() {
        //在构造器中加个逻辑判断,多次调用抛出异常
        if (instance != null) {
            throw new RuntimeException("防止通过反射创建实例");
        }
    }

    //类加载时，自行实例化
    //如果存在释放资源的情况下，就不能加final修饰了
    private static final SingletonAA instance = new SingletonAA();

    public static SingletonAA getInstance() {
        return instance;
    }

}

/**
 * 防序列化
 */
class SingletonAAA implements Serializable {

    private static final long serialVersionUID = -1715283651553593107L;

    //私有构造器
    private SingletonAAA() {
    }

    //类加载时，自行实例化
    //如果存在释放资源的情况下，就不能加final修饰了
    private static final SingletonAAA instance = new SingletonAAA();

    public static SingletonAAA getInstance() {
        return instance;
    }

    //反序列化定义该方法，则不需要创建新对象
    private Object readResolve() throws ObjectStreamException{
        return instance;
    }
}

/**
 * 单例-状态化
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