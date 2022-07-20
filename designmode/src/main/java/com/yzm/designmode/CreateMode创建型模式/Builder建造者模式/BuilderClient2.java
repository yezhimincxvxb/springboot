package com.yzm.designmode.CreateMode创建型模式.Builder建造者模式;

public class BuilderClient2 {
    public static void main(String[] args) {
        //葡萄
        Director director = new Director();
        Product grape = director.grape();
        System.out.println(grape);
        System.out.println();

        //葡萄干
        Product raisin = director.raisin();
        System.out.println(raisin);
        System.out.println();

        //葡萄汁
        Product grapeJuice = director.grapeJuice();
        System.out.println(grapeJuice);
        System.out.println();

        //葡萄酒
        Product wine = director.wine();
        System.out.println(wine);
    }
}

/**
 * 步骤 1
 * 建造者（生产商）角色
 * 提供多种方式进行构造，最终返回产品对象
 */
abstract class Builder {
    Product product = new Product();

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

/**
 * 步骤 2
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
 * 步骤 3
 * 主导者角色(监工)
 * 组合不同的方式，使得同样的构建过程可以创建不同的表示。
 */
class Director {

    public Product grape() {
        ConcreteBuilder builder = new ConcreteBuilder();
        builder.grape();
        return builder.getResult();
    }

    public Product raisin() {
        ConcreteBuilder builder = new ConcreteBuilder();
        builder.raisin();
        builder.airDry();
        return builder.getResult();
    }

    public Product grapeJuice() {
        ConcreteBuilder builder = new ConcreteBuilder();
        builder.grapeJuice();
        builder.juicing();
        return builder.getResult();
    }

    public Product wine() {
        ConcreteBuilder builder = new ConcreteBuilder();
        builder.wine();
        builder.juicing();
        builder.yeast();
        return builder.getResult();
    }
}
