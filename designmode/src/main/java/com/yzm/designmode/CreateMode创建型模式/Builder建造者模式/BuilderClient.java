package com.yzm.designmode.CreateMode创建型模式.Builder建造者模式;

import java.util.ArrayList;
import java.util.List;

/**
 * 建造者模式（Builder Pattern）使用多个简单的对象一步一步构建成一个复杂的对象。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。
 * 一个 Builder 类会一步一步构造最终的对象。该 Builder 类是独立于其他对象的。
 * <p>
 * 意图：将一个复杂的构建与其表示相分离，使得同样的构建过程可以创建不同的表示。
 * 主要解决：主要解决在软件系统中，有时候面临着"一个复杂对象"的创建工作，其通常由各个部分的子对象用一定的算法构成；
 * 由于需求的变化，这个复杂对象的各个部分经常面临着剧烈的变化，但是将它们组合在一起的算法却相对稳定。
 * 何时使用：一些基本部件不会变，而其组合经常变化的时候。
 * 如何解决：将变与不变分离开。
 * 关键代码：建造者：创建和提供实例，导演：管理建造出来的实例的依赖关系。
 * 应用实例： 1、去肯德基，汉堡、可乐、薯条、炸鸡翅等是不变的，而其组合是经常变化的，生成出所谓的"套餐"。 2、JAVA 中的 StringBuilder。
 * 优点： 1、建造者独立，易扩展。 2、便于控制细节风险。
 * 缺点： 1、产品必须有共同点，范围有限制。 2、如内部变化复杂，会有很多的建造类。
 * 使用场景： 1、需要生成的对象具有复杂的内部结构。 2、需要生成的对象内部属性本身相互依赖。
 * 注意事项：与工厂模式的区别是：建造者模式更加关注与零件装配的顺序。
 */
public class BuilderClient {
    public static void main(String[] args) {
        System.out.println("套餐一");
        OrderDirector director = new OrderDirector();
        Order packageA = director.packageA();
        packageA.showItems();
        System.out.println("总价: " + packageA.getTotal());

        System.out.println();
        System.out.println("套餐二");
        Order packageB = director.packageB();
        packageB.showItems();
        System.out.println("总价: " + packageB.getTotal());
    }
}

/**
 * 步骤 1
 * 食品(物品)超类
 */
interface Item {
    String name();

    float price();

    Packing packing();
}

/**
 * 步骤 2
 * 包装方式主要分两种
 * 主食用纸盒包装
 * 饮料用杯子装
 */
interface Packing {
    String pack();
}

class Carton implements Packing {

    @Override
    public String pack() {
        return "纸盒";
    }
}

class Cup implements Packing {

    @Override
    public String pack() {
        return "杯子";
    }
}

/**
 * 步骤 3
 * 主食：汉堡、鸡肉卷、三明治
 */
abstract class StapleFood implements Item {

    @Override
    public Packing packing() {
        return new Carton();
    }

    @Override
    public abstract float price();
}

class Hamburger extends StapleFood {

    @Override
    public String name() {
        return "汉堡";
    }

    @Override
    public float price() {
        return 20.0F;
    }
}

class ChickenRolls extends StapleFood {

    @Override
    public String name() {
        return "鸡肉卷";
    }

    @Override
    public float price() {
        return 25.0F;
    }
}

class Sandwich extends StapleFood {

    @Override
    public String name() {
        return "三明治";
    }

    @Override
    public float price() {
        return 15.0F;
    }
}

/**
 * 步骤 4
 * 饮料：可乐、果汁
 */
abstract class Drinks implements Item {

    @Override
    public Packing packing() {
        return new Cup();
    }

    @Override
    public abstract float price();
}

class Cola extends Drinks {

    @Override
    public String name() {
        return "可乐";
    }

    @Override
    public float price() {
        return 7.0F;
    }
}

class Juice extends Drinks {

    @Override
    public String name() {
        return "果汁";
    }

    @Override
    public float price() {
        return 6.0F;
    }
}

/**
 * 实体角色
 */
class Order {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public float getTotal() {
        float cost = 0.0f;
        for (Item item : items) {
            cost += item.price();
        }
        return cost;
    }

    public void showItems() {
        for (Item item : items) {
            System.out.print("物品 : " + item.name());
            System.out.print(", 价格 : " + item.price());
            System.out.println(", Packing : " + item.packing().pack());
        }
    }
}

/**
 * 建造者角色
 */
class OrderBuilder {
    private final Order order = new Order();

    public void addItem(Item item) {
        order.addItem(item);
    }

    public Order build() {
        return order;
    }
}

/**
 * 主导者角色
 */
class OrderDirector {

    public Order packageA() {
        OrderBuilder builder = new OrderBuilder();
        builder.addItem(new Hamburger());
        builder.addItem(new Cola());
        return builder.build();
    }

    public Order packageB() {
        OrderBuilder builder = new OrderBuilder();
        builder.addItem(new ChickenRolls());
        builder.addItem(new Sandwich());
        builder.addItem(new Juice());
        return builder.build();
    }
}