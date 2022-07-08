package com.yzm.designmode.F代理模式Proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB动态代理
 * Jdk的动态代理依靠接口实现，如果有些类并没有实现接口，则不能使用jdk代理，这就要用到CGLIB代理了。
 * CGLIB是针对类来实现的，他的原理是对指定的目标类生成一个子类，并覆盖其中方法实现增强，但因为采用的是继承，所以不能对final修饰的类进行代理。
 */
public class CGLIBDynamicProxyDemo {

    public static void main(String[] args) {
        ProxyCglib proxy = new ProxyCglib(new RealSubjectCglib());
        RealSubjectCglib subjectCglib = (RealSubjectCglib) proxy.getInstance();
        String operate = subjectCglib.operate();
        System.out.println("operate = " + operate);
    }
}

class ProxyCglib implements MethodInterceptor {
    private Object targetObject;

    public ProxyCglib(Object targetObject) {
        this.targetObject = targetObject;
    }

    public Object getInstance() {
        //Cglib中的加强器，类似于咱们JDK动态代理中的Proxy类，该类就是用来获取代理对象的
        Enhancer enhancer = new Enhancer();
        //设置父类的字节码对象。为啥子要这样做呢？因为使用CGLIB生成的代理类是属于目标类的子类的，也就是说代理类是要继承自目标类的
        enhancer.setSuperclass(targetObject.getClass());
        //设置回调，这里相当于是对于代理类上所有方法的调用，都会调用Callback，而Callback则需要实现intercept()方法进行拦截
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("调用方法名：" +method.getName());
        Object object = proxy.invokeSuper(obj, args);
        System.out.println("返回结果：" + object);
        return object;
    }
}

/**
 * 目标对象
 */
class RealSubjectCglib  {
    public String operate() {
        return "RealSubjectCglib ";
    }
}