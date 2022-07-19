package com.yzm.designmode.CreateMode创建型模式.Prototype原型模式;

public class DeepCloneClient {

    public static void main(String[] args) {
        try {
            MyPro2 pro = new MyPro2("管理员");
            MyPrototype2 prototype = new MyPrototype2("admin", "123456", pro);
            MyPrototype2 clone = (MyPrototype2) prototype.clone();

            System.out.println("prototype2 = " + prototype);
            System.out.println("clone2 = " + clone);
            System.out.println(prototype == clone);

            clone.setPassword("666666");
            clone.getPro().setDesc("超级管理员");
            System.out.println("prototype2 = " + prototype);
            System.out.println("clone2 = " + clone);
        } catch (CloneNotSupportedException e) {
            System.out.println("MyPrototype is not Cloneable");
        }
    }

}

/**
 * 深克隆(值和引用拷贝)
 */
class MyPrototype2 implements Cloneable {

    private String username;
    private String password;

    private MyPro2 pro;

    public MyPrototype2(String username, String password, MyPro2 pro) {
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

    public MyPro2 getPro() {
        return pro;
    }

    public void setPro(MyPro2 pro) {
        this.pro = pro;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        MyPrototype2 prototype = (MyPrototype2) super.clone();
        prototype.pro = (MyPro2) pro.clone();
        return prototype;
    }

    @Override
    public String toString() {
        return "MyPrototype2{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pro=" + pro +
                '}';
    }
}

/**
 * 引用类型字段也实现 Cloneable 接口
 */
class MyPro2 implements Cloneable {
    private String desc;

    public MyPro2(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "MyPro2{" +
                "desc='" + desc + '\'' +
                '}';
    }
}
