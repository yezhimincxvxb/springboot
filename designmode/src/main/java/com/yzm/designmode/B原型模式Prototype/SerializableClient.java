package com.yzm.designmode.B原型模式Prototype;

import java.io.*;

public class SerializableClient {

    public static void main(String[] args) {
        MyPro3 pro = new MyPro3("管理员");
        MyPrototype3 prototype = new MyPrototype3("admin", "123456", pro);
        MyPrototype3 clone = prototype.serialization();

        System.out.println("prototype3 = " + prototype);
        System.out.println("clone3 = " + clone);
        System.out.println(prototype == clone);

        clone.setPassword("666666");
        clone.getPro().setDesc("超级管理员");
        System.out.println("prototype3 = " + prototype);
        System.out.println("clone3 = " + clone);
    }

}

/**
 * 深克隆
 * 实现序列化 Serializable 接口
 */
class MyPrototype3 implements Serializable {

    private static final long serialVersionUID = 1526132947243545151L;
    private String username;
    private String password;

    private MyPro3 pro;

    public MyPrototype3(String username, String password, MyPro3 pro) {
        this.username = username;
        this.password = password;
        this.pro = pro;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MyPro3 getPro() {
        return pro;
    }

    public void setPro(MyPro3 pro) {
        this.pro = pro;
    }

    public MyPrototype3 serialization() {
        MyPrototype3 prototype = null;
        try {
            //序列化
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            //反序列化
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            prototype = (MyPrototype3) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return prototype;
    }

    @Override
    public String toString() {
        return "MyPrototype3{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pro=" + pro +
                '}';
    }
}

/**
 * 引用类型字段也需要实现序列化接口
 */
class MyPro3 implements Serializable {
    private static final long serialVersionUID = -8067861151689905693L;
    private String desc;

    public MyPro3(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "MyPro3{" +
                "desc='" + desc + '\'' +
                '}';
    }
}