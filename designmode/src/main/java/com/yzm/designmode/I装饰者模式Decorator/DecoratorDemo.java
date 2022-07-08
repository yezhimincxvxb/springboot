package com.yzm.designmode.I装饰者模式Decorator;

/**
 * 装饰者模式
 * Decorator 定义:
 * 动态给一个对象添加一些额外的职责,就象在墙上刷油漆.使用 Decorator 模式相比用生成子类方式达到功能的扩充显得更为灵活.
 */
public class DecoratorDemo {
    public static void main(String[] args) {
        Coffee blackCoffee = new BlackCoffee();
        Coffee whiteCoffee = new WhiteCoffee();

        Coffee milk = new Milk(blackCoffee);
        Coffee sugar = new Sugar(blackCoffee);
        System.out.println(milk.getDescription() + " 价格：" + milk.getPrice());
        System.out.println(sugar.getDescription() + " 价格：" + sugar.getPrice());

        Coffee milk2 = new Milk(whiteCoffee);
        Coffee sugar2 = new Sugar(whiteCoffee);
        System.out.println(milk2.getDescription() + " 价格：" + milk2.getPrice());
        System.out.println(sugar2.getDescription() + " 价格：" + sugar2.getPrice());
    }
}

/**
 * 装饰的主体 咖啡
 * 接口，规范准备接收增强功能的类
 */
interface Coffee {
    //商品描述
    String getDescription();

    //商票价格
    double getPrice();
}

/**
 * 装饰主体的具体类型 咖啡种类 白咖啡黑咖啡
 * 准备接收增强功能的类
 */
class BlackCoffee implements Coffee {
    private String description = "黑咖啡";
    private double price = 50D;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }
}

class WhiteCoffee implements Coffee {
    private String description = "白咖啡";
    private double price = 30D;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }
}

/**
 * 装饰者：规范新增功能的类
 */
class Decorator implements Coffee {
    @Override
    public String getDescription() {
        return "我只是装饰器，我什么都不知道";
    }

    @Override
    public double getPrice() {
        return 0;
    }
}

/**
 * 装饰者的具体实现(新增功能)：加牛奶、加糖、加冰等
 */
class Milk extends Decorator {
    private String description = "加牛奶";
    private double price = 5D;

    private Coffee coffee;

    public Milk(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + "," + description;
    }

    @Override
    public double getPrice() {
        return coffee.getPrice() + price;
    }
}

class Sugar extends Decorator {
    private String description = "加糖";
    private double price = 3D;

    private Coffee coffee;

    public Sugar(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + "," + description;
    }

    @Override
    public double getPrice() {
        return coffee.getPrice() + price;
    }
}
