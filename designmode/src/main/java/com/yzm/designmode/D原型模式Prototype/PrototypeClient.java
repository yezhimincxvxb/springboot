package com.yzm.designmode.D原型模式Prototype;

/**
 * 原型模式定义:
 * 用原型实例指定创建对象的种类,并且通过拷贝这些原型创建新的对象
 *
 * 原型模式是一个创建型的模式。原型二字表明了改模式应该有一个样板实例，用户从这个样板对象中复制一个内部属性一致的对象，
 * 这个过程也就是我们称的“克隆”。被复制的实例就是我们所称的“原型”，这个原型是可定制的。
 * 原型模式多用于创建复杂的或者构造耗时的实例，因为这种情况下，复制一个已经存在的实例可使程序运行更高效。
 */
public class PrototypeClient {

    public static void main(String[] args) {
        try {
            MyPro pro = new MyPro("管理员");
            MyPrototype prototype = new MyPrototype("admin","123456", pro);
            MyPrototype clone = (MyPrototype) prototype.clone();

            System.out.println("prototype = " + prototype);
            System.out.println("clone = " + clone);
            System.out.println(prototype == clone);

            clone.setPassword("666666");
            clone.getPro().setDesc("超级管理员");
            System.out.println("prototype = " + prototype);
            System.out.println("clone = " + clone);
        } catch (CloneNotSupportedException e) {
            System.out.println("MyPrototype is not Cloneable");
        }
    }
}

/**
 * 浅克隆(值拷贝)
 * 克隆对象修改8大基本属性和字符串不影响原型对象
 * 克隆对象修改数组、集合和引用对象时会影响原型对象
 */
class MyPrototype implements Cloneable{

    private String username;
    private String password;

    private MyPro pro;

    public MyPrototype(String username, String password, MyPro pro) {
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

    public MyPro getPro() {
        return pro;
    }

    public void setPro(MyPro pro) {
        this.pro = pro;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
            return super.clone();
    }

    @Override
    public String toString() {
        return "MyPrototype{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pro=" + pro +
                '}';
    }
}

class MyPro {
    private String desc;

    public MyPro (String desc) {
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
        return "MyPro{" +
                "desc='" + desc + '\'' +
                '}';
    }
}