package com.yzm.designmode.StructuralPattern结构型模式.Proxy代理模式;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 动态代理
 * 静态代理只服务一种类型的目标对象
 * 动态代理可服务多种不同类型的目标对象
 * 动态代理实现原理：反射机制
 */
public class DynamicProxyClient {

    public static void main(String[] args) throws ParseException {
        Subject2 buy1 = (Subject2) new DynamicProxyHandler(new Buyer1("小明")).newProxyInstance();
        buy1.buy("进口汽车");
        System.out.println(buy1.pay(55000.0D));
        System.out.println();

        Subject2 buy2 = (Subject2) new DynamicProxyHandler(new Buyer2("小红")).newProxyInstance();
        buy2.buy("LV包包");
        System.out.println(buy2.pay(9998.0D));
    }
}

/**
 * 实现处理器类 InvocationHandler
 */
class DynamicProxyHandler implements InvocationHandler {

    // 目标对象，被代理对象
    private final Object targetObject;

    public DynamicProxyHandler(Object targetObject) {
        this.targetObject = targetObject;
    }

    /**
     * Proxy.newProxyInstance（）作用：根据指定的类装载器、一组接口 & 调用处理器 生成动态代理类实例，并最终返回
     * 参数1：指定产生代理对象的类加载器，需要将其指定为和目标对象同一个类加载器
     * 参数2：指定目标对象的实现接口，使代理对象也能默认的实现该接口
     * 参数3：指定InvocationHandler对象。即动态代理对象在调用方法时，会关联到哪个InvocationHandler对象
     *
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
        if (method.getName().equals("pay") && (double) args[0] > 10000.0D) {
            System.out.println("支付金额超过1W需交税");
        }
        //获取方法返回值
        Object invoke = method.invoke(targetObject, args);
        if (invoke != null) {
            if ("1".equals(invoke)) {
                System.out.println("支付成功！");
            } else {
                System.out.println("支付失败！");
            }
        }
        return invoke;
    }
}

/**
 * 声明目标对象的抽象接口
 */
interface Subject2 {
    void buy(String goods);

    String pay(double price);
}

class Buyer1 implements Subject2 {
    String name;

    public Buyer1(String name) {
        this.name = name;
    }

    @Override
    public void buy(String goods) {
        System.out.println(name + "购买" + goods);
    }

    @Override
    public String pay(double price) {
        System.out.println(name + "支付" + price);
        return "1";
    }


}

class Buyer2 implements Subject2 {
    String name;

    public Buyer2(String name) {
        this.name = name;
    }

    @Override
    public void buy(String goods) {
        System.out.println(name + "购买" + goods);
    }

    @Override
    public String pay(double price) {
        System.out.println(name + "支付" + price);
        return "0";
    }
}