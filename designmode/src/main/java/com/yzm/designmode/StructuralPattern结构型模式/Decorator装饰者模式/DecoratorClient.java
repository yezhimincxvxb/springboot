package com.yzm.designmode.StructuralPattern结构型模式.Decorator装饰者模式;

/**
 * 装饰者模式
 * 装饰器模式（CoffeeDecorator Pattern）允许向一个现有的对象添加新的功能，同时又不改变其结构。
 * 这种类型的设计模式属于结构型模式，它是作为现有的类的一个包装。
 * 这种模式创建了一个装饰类，用来包装原有的类，并在保持类方法签名完整性的前提下，提供了额外的功能。
 * <p>
 * 意图：动态地给一个对象添加一些额外的职责。就增加功能来说，装饰器模式相比生成子类更为灵活。
 * 主要解决：一般的，我们为了扩展一个类经常使用继承方式实现，由于继承为类引入静态特征，并且随着扩展功能的增多，子类会很膨胀。
 * 何时使用：在不想增加很多子类的情况下扩展类。
 * 如何解决：将具体功能职责划分，同时继承装饰者模式。
 * 关键代码： 1、Component 类充当抽象角色，不应该具体实现。 2、修饰类引用和继承 Component 类，具体扩展类重写父类方法。
 * 应用实例： 1、孙悟空有 72 变，当他变成"庙宇"后，他的根本还是一只猴子，但是他又有了庙宇的功能。
 * 2、不论一幅画有没有画框都可以挂在墙上，但是通常都是有画框的，并且实际上是画框被挂在墙上。
 * 在挂在墙上之前，画可以被蒙上玻璃，装到框子里；这时画、玻璃和画框形成了一个物体。
 * 优点：装饰类和被装饰类可以独立发展，不会相互耦合，装饰模式是继承的一个替代模式，装饰模式可以动态扩展一个实现类的功能。
 * 缺点：多层装饰比较复杂。
 * 使用场景： 1、扩展一个类的功能。 2、动态增加功能，动态撤销。
 * 注意事项：可代替继承。
 */
public class DecoratorClient {
    public static void main(String[] args) {
        Coffee blackCoffee = new BlackCoffee();
        Coffee whiteCoffee = new WhiteCoffee();

        Coffee milk = new Milk(blackCoffee);
        Coffee sugar = new Sugar(blackCoffee);
        System.out.println(milk.description() + " 价格：" + milk.price());
        System.out.println(sugar.description() + " 价格：" + sugar.price());

        Coffee milk2 = new Milk(whiteCoffee);
        Coffee sugar2 = new Sugar(whiteCoffee);
        System.out.println(milk2.description() + " 价格：" + milk2.price());
        System.out.println(sugar2.description() + " 价格：" + sugar2.price());
    }
}

/**
 * 装饰的主体 咖啡
 * 接口，规范准备接收增强功能的类
 */
interface Coffee {
    //商品描述
    String description();

    //商票价格
    double price();
}

/**
 * 装饰主体的具体类型 咖啡种类 白咖啡黑咖啡
 * 准备接收增强功能的类
 */
class BlackCoffee implements Coffee {
    String description = "黑咖啡";
    double price = 50D;

    @Override
    public String description() {
        return description;
    }

    @Override
    public double price() {
        return price;
    }
}

class WhiteCoffee implements Coffee {
    String description = "白咖啡";
    double price = 30D;

    @Override
    public String description() {
        return description;
    }

    @Override
    public double price() {
        return price;
    }
}

/**
 * 装饰者：规范新增功能的类
 */
abstract class CoffeeDecorator implements Coffee {
    protected String description;
    protected double price;
    protected Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    public abstract String description();

    public abstract double price();

}

/**
 * 装饰者的具体实现(新增功能)：加牛奶、加糖、加冰等
 */
class Milk extends CoffeeDecorator {

    public Milk(Coffee coffee) {
        super(coffee);
        this.description = "加牛奶";
        this.price = 10.0D;
    }

    @Override
    public String description() {
        return coffee.description() + ", " + description;
    }

    @Override
    public double price() {
        return coffee.price() + price;
    }
}

class Sugar extends CoffeeDecorator {

    public Sugar(Coffee coffee) {
        super(coffee);
        this.description = "加糖";
        this.price = 5.0D;
    }

    @Override
    public String description() {
        return coffee.description() + ", " + description;
    }

    @Override
    public double price() {
        return coffee.price() + price;
    }
}
