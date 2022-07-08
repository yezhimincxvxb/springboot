package com.yzm.designmode.F代理模式Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * 静态代理只服务一种类型的目标对象
 * 动态代理可服务多种不同类型的目标对象
 * 动态代理实现原理：反射机制
 */
public class DynamicProxyDemo {

    public static void main(String[] args) {
        Subject2 buy1 = (Subject2) new DynamicProxyHandler(new Buyer1()).newProxyInstance();
        Subject2 buy2 = (Subject2) new DynamicProxyHandler(new Buyer2()).newProxyInstance();

        System.out.println(buy1.name());
        buy1.buy();
        System.out.println(buy2.name());
        buy2.buy();
    }
}

/**
 * 步骤1： 声明 调用处理器类
 */
class DynamicProxyHandler implements InvocationHandler {

    // 目标对象，被代理对象
    private Object targetObject;

    public DynamicProxyHandler(Object targetObject) {
        this.targetObject = targetObject;
    }

    /**
     * Proxy.newProxyInstance（）作用：根据指定的类装载器、一组接口 & 调用处理器 生成动态代理类实例，并最终返回
     * 参数1：指定产生代理对象的类加载器，需要将其指定为和目标对象同一个类加载器
     * 参数2：指定目标对象的实现接口，使代理对象也能默认的实现该接口
     * 参数3：指定InvocationHandler对象。即动态代理对象在调用方法时，会关联到哪个InvocationHandler对象
     * @return
     */
    public Object newProxyInstance() {
        return Proxy.newProxyInstance(
                targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(),
                this);
    }

    /**
     * @param proxy  动态代理对象
     * @param method 目标对象被调用的方法
     * @param args   指定被调用方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("buy")) {
            System.out.println("买东西，找代购");
        }
        return method.invoke(targetObject, args);
    }
}

/**
 * 步骤2： 声明目标对象的抽象接口
 */
interface Subject2 {
    void buy();
    String name();
}

class Buyer1 implements Subject2 {

    @Override
    public void buy() {
        System.out.println("买进口汽车");
    }

    @Override
    public String name() {
        return "小明";
    }

}

class Buyer2 implements Subject2 {

    @Override
    public void buy() {
        System.out.println("买LV包包");
    }

    @Override
    public String name() {
        return "小强";
    }

}