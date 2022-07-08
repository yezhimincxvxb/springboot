package com.yzm.designmode.D原型模式Prototype;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

public class CloneDemo {

    public static void main(String[] args) {
//        test01();
//        test02();
        test03();
    }

    //BeanUtils.copyProperties 将原型对象属性赋值给另一个对象，浅克隆
    private static void test01() {
        C c = new C("管理员");
        A a = new A("admin", "123456", c);
        A a1 = new A();
        BeanUtils.copyProperties(a, a1);
        System.out.println("a = " + a);
        System.out.println("a1 = " + a1);
        a1.setPassword("666666");
        a1.getC().setDesc("超级管理员");
        System.out.println("a = " + a);
        System.out.println("a1 = " + a1);
        System.out.println("--------------------------");
        B b = new B();
        BeanUtils.copyProperties(a, b);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        b.setPassword("88888");
        b.getC().setDesc("普通用户");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    //SerializationUtils.clone 要求原型对象必须实现序列化接口，否则报NotSerializableException异常，深克隆
    private static void test02() {
        C c = new C("管理员");
        A a = new A("admin", "123456", c);
        A clone = SerializationUtils.clone(a);
        System.out.println("a = " + a);
        System.out.println("clone = " + clone);
        clone.setPassword("88888");
        clone.getC().setDesc("普通用户");
        System.out.println("a = " + a);
        System.out.println("clone = " + clone);
    }

    //JSON.parseObject 要求原型对象里面的引用对象是public修饰的 深克隆
    private static void test03() {
        C c = new C("管理员");
        A a = new A("admin", "123456", c);
        String json = JSON.toJSONString(a);
        System.out.println("json = " + json);
        A clone = JSON.parseObject(json,A.class);
        System.out.println("a = " + a);
        System.out.println("clone = " + clone);
        clone.setPassword("66666");
        clone.getC().setDesc("超级管理员");
        System.out.println("a = " + a);
        System.out.println("clone = " + clone);
        System.out.println("-------------------------");
        B b = JSON.parseObject(json,B.class);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        b.setPassword("88888");
        b.getC().setDesc("普通用户");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class A implements Serializable {
    private static final long serialVersionUID = 8472126398016498668L;
    private String username;
    private String password;

    private C c;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class B {
    private String username;
    private String password;

    private C c;
}

//@Data
//@AllArgsConstructor
//class C implements Serializable{
//    private static final long serialVersionUID = 7096298633803818663L;
//    private String desc;
//}

