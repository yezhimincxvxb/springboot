package com.yzm.designmode.CreateMode创建型模式.Builder建造者模式;

/**
 * 建造者模式 与 工厂模式
 *
 * 工厂模式: 重点是获取实例对象
 * 作用：实现了创建者和调用者的分离。
 * 意义：将实例化对象的代码提取出来，放到一个类(工厂类)中统一管理和维护，达到和主项目的依赖关系的解耦。从而提高项目的扩展和维护性。
 * 分类：简单工厂模式、工厂方法模式、抽象工厂模式
 *
 * 建造者模式: 可以根据不同的行为(调用顺序)构建实例对象
 * 将一个复杂对象的构建与它的表示分离,使得同样的构建过程可以创建不同的表示.
 */
public class BuilderClient {
    public static void main(String[] args) {
        //主导者跟生产商建立联系
        Builder builder = new ConcreteBuilder();
        Director director = new Director(builder);

        //通知生产商，生产产品
        Product grape = director.grape();
        System.out.println(grape);
        System.out.println();

        Product raisin = director.raisin();
        System.out.println(raisin);
        System.out.println();

        Product grapeJuice = director.grapeJuice();
        System.out.println(grapeJuice);
        System.out.println();

        Product wine = director.wine();
        System.out.println(wine);
    }
}

/**
 * 建造者（生产商）角色
 */
abstract class Builder {
    Product product = new Product();;

    abstract void grape();

    abstract void raisin();

    abstract void airDry();

    abstract void grapeJuice();

    abstract void juicing();

    abstract void wine();

    abstract void yeast();

    Product getResult() {
        return product;
    }
}

/**
 * 具体生产细节（工序）
 */
class ConcreteBuilder extends Builder {

    @Override
    public void grape() {
        System.out.println("直接出售");
        product.setName("葡萄");
        product.setPrice(10.0D);
    }

    @Override
    public void raisin() {
        product.setName("葡萄干");
    }

    @Override
    public void airDry() {
        System.out.println("风干");
        product.setPrice(product.getPrice() + 12.0D);
    }

    @Override
    public void grapeJuice() {
        product.setName("葡萄汁");
    }

    @Override
    public void juicing() {
        System.out.println("榨汁");
        product.setPrice(product.getPrice() + 7.0D);
    }

    @Override
    public void wine() {
        product.setName("葡萄酒");
    }

    @Override
    public void yeast() {
        System.out.println("发酵");
        product.setPrice(product.getPrice() + 110.0D);
    }

}

/**
 * 主导者角色，该角色不是必须的
 * 决定售卖后，通知生产商
 */
class Director {
    private final Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public Product grape() {
        builder.grape();
        return builder.getResult();
    }

    public Product raisin() {
        builder.raisin();
        builder.airDry();
        return builder.getResult();
    }

    public Product grapeJuice() {
        builder.grapeJuice();
        builder.juicing();
        return builder.getResult();
    }

    public Product wine() {
        builder.wine();
        builder.juicing();
        builder.yeast();
        return builder.getResult();
    }
}

/**
 * 产品类
 */
class Product {
    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
