package com.yzm.annotation.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
@Configuration
@EnableAspectJAutoProxy
public class AopPointcut {

    /**
     * 使用Pointcut定义切点
     * 由于Spring切面粒度最小是达到方法级别，而execution表达式可以用于明确指定方法返回类型，
     * 类名，方法名和参数名等与方法相关的部件，并且在Spring中，大部分需要使用AOP的业务场景也只需要达到方法级别即可，
     * 因而execution表达式的使用是最为广泛的
     */
    @Pointcut("execution(* com.yzm.annotation.aop.AopService.*())")
    private void myPointcut() {
    }

    /**
     * 前置通知
     */
    @Before("execution(* com.yzm.annotation.aop.AopService.*(..))")
    public void before(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        System.out.println(name + "前置通知....");
    }

    /**
     * 后置通知
     * returnVal,切点方法执行后的返回值
     */
    @AfterReturning(value = "myPointcut()", returning = "returnVal")
    public void AfterReturning(JoinPoint joinPoint, Object returnVal) {
        System.out.println(joinPoint.getSignature().getName() + "后置通知...." + returnVal);
    }


    /**
     * 异常通知
     */
    @AfterThrowing(value = "myPointcut()", throwing = "e")
    public void afterThrowable(JoinPoint joinPoint,Throwable e) {
        System.out.println("出现异常:msg=" + e.getMessage());
    }

    /**
     * 最终通知：无论什么情况下都会执行的方法
     * 最终通知比后置通知先执行
     */
    @After(value = "myPointcut()")
    public void after(JoinPoint joinPoint) {
        System.out.println("最终通知....");
    }

//    /**
//     * 环绕通知
//     */
//    @Around("myPointcut()")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("环绕通知前....");
//        Object obj = joinPoint.proceed();
//        System.out.println("环绕通知后....");
//        return obj;
//    }

}
