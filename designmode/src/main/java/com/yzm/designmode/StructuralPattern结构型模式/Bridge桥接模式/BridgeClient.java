package com.yzm.designmode.StructuralPattern结构型模式.Bridge桥接模式;

import java.text.ParseException;

/**
 * 桥接模式
 * 桥接（Bridge）是用于把抽象化与实现化解耦，使得二者可以独立变化。这种类型的设计模式属于结构型模式，它通过提供抽象化和实现化之间的桥接结构，来实现二者的解耦。
 * 这种模式涉及到一个作为桥接的接口，使得实体类的功能独立于接口实现类。这两种类型的类可被结构化改变而互不影响。
 * <p>
 * 意图：将抽象部分与实现部分分离，使它们都可以独立的变化。
 * 主要解决：在有多种可能会变化的情况下，用继承会造成类爆炸问题，扩展起来不灵活。
 * 何时使用：实现系统可能有多个角度分类，每一种角度都可能变化。
 * 如何解决：把这种多角度分类分离出来，让它们独立变化，减少它们之间耦合。
 * 关键代码：抽象类依赖实现类。
 * 应用实例： 1、猪八戒从天蓬元帅转世投胎到猪，转世投胎的机制将尘世划分为两个等级，即：灵魂和肉体，前者相当于抽象化，后者相当于实现化。生灵通过功能的委派，调用肉体对象的功能，使得生灵可以动态地选择。
 * 2、墙上的开关，可以看到的开关是抽象的，不用管里面具体怎么实现的。
 * 优点： 1、抽象和实现的分离。 2、优秀的扩展能力。 3、实现细节对客户透明。
 * 缺点：桥接模式的引入会增加系统的理解与设计难度，由于聚合关联关系建立在抽象层，要求开发者针对抽象进行设计与编程。
 * 使用场景： 1、如果一个系统需要在构件的抽象化角色和具体化角色之间增加更多的灵活性，避免在两个层次之间建立静态的继承联系，通过桥接模式可以使它们在抽象层建立一个关联关系。
 * 2、对于那些不希望使用继承或因为多层次继承导致系统类的个数急剧增加的系统，桥接模式尤为适用。
 * 3、一个类存在两个独立变化的维度，且这两个维度都需要进行扩展。
 * 注意事项：对于两个独立变化的维度，使用桥接模式再适合不过了。
 */
public class BridgeClient {
    public static void main(String[] args) throws ParseException {
        //颜料桶
        Color red = new Red();
        Color green = new Green();
        Color blue = new Blue();

        //跟图形染色
        Shape circle = new Circle(red);
        circle.draw();
        System.out.println();

        Shape rectangle = new Rectangle(blue);
        rectangle.draw();
        System.out.println();

        Shape square = new Square(green);
        square.draw();
        square.setColor(red);
        square.draw();
        square.setColor(blue);
        square.draw();
    }
}

/**
 * 步骤 1 扮演抽象化角色
 * 定义形状抽象类、颜色接口
 * 桥接模式（形状是一个变化维度，颜色是一个变化维度，两个维度不相互影响）
 * 如果有3种形状、3种颜色就可以桥接出 3 * 3 种不同带颜色的形状
 */
abstract class Shape {
    protected Color color;

    protected Shape(Color color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract void draw();
}

/**
 * 扮演实现化角色
 */
interface Color {
    void dyeing(String shape);
}

/**
 * 步骤 2
 */
class Green implements Color {

    @Override
    public void dyeing(String shape) {
        System.out.println("绿色的" + shape);
    }

}

class Red implements Color {

    @Override
    public void dyeing(String shape) {
        System.out.println("红色的" + shape);
    }

}

class Blue implements Color {

    @Override
    public void dyeing(String shape) {
        System.out.println("蓝色的" + shape);
    }

}

class Circle extends Shape {

    protected Circle(Color color) {
        super(color);
    }

    @Override
    public void draw() {
        color.dyeing("圆形");
    }
}

class Square extends Shape {

    protected Square(Color color) {
        super(color);
    }

    @Override
    public void draw() {
        color.dyeing("正方形");
    }
}

class Rectangle extends Shape {

    protected Rectangle(Color color) {
        super(color);
    }

    @Override
    public void draw() {
        color.dyeing("长方形");
    }
}