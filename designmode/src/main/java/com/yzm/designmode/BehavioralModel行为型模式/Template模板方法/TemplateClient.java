package com.yzm.designmode.BehavioralModel行为型模式.Template模板方法;

/**
 * 模板方法模式
 * 定义一个模板结构，将具体内容延迟到子类去实现。
 * <p>
 * 提高代码复用性
 * 将相同部分的代码放在抽象的父类中，而将不同的代码放入不同的子类中
 * 实现了反向控制
 * 通过一个父类调用其子类的操作，通过对子类的具体实现扩展不同的行为，实现了反向控制 & 符合"开闭原则"
 */
public class TemplateClient {
    public static void main(String[] args) {
        System.out.println("--------制作辣椒包菜--------");
        Cook baoCai = new BaoCai();
        baoCai.cookProcess();

        System.out.println("--------制作蒜蓉菜心--------");
        Cook caiXin = new CaiXin();
        caiXin.cookProcess();
    }
}


abstract class Cook {
    //模板方法，用来控制炒菜的流程 （炒菜的流程是一样的-复用）
    //申明为final，不希望子类覆盖这个方法，防止更改流程的执行顺序 
    final void cookProcess() {
        //第一步：倒油
        this.pourOil();
        //第二步：热油
        this.HeatOil();
        //第三步：倒蔬菜
        this.pourVegetable();
        //第四步：倒调味料
        this.pourSauce();
        //第五步：翻炒
        this.fry();
    }

    //定义结构里哪些方法是所有过程都是一样的可复用的，哪些是需要子类进行实现的

    //第一步：倒油是一样的，所以直接实现
    void pourOil() {
        System.out.println("倒油");
    }

    //第二步：热油是一样的，所以直接实现
    void HeatOil() {
        System.out.println("热油");
    }

    //第三步：倒蔬菜是不一样的（一个下包菜，一个是下菜心）
    //所以声明为抽象方法，具体由子类实现
    abstract void pourVegetable();

    //第四步：倒调味料是不一样的（一个下辣椒，一个是下蒜蓉）
    //所以声明为抽象方法，具体由子类实现
    abstract void pourSauce();

    //第五步：翻炒是一样的，所以直接实现
    void fry() {
        System.out.println("炒啊炒啊炒到熟啊");
    }
}

/**
 * 炒手撕包菜的类
 */
class BaoCai extends Cook {
    @Override
    public void pourVegetable() {
        System.out.println("下包菜");
    }

    @Override
    public void pourSauce() {
        System.out.println("倒辣椒酱");
    }
}

/**
 * 炒蒜蓉菜心的类
 */
class CaiXin extends Cook {
    @Override
    public void pourVegetable() {
        System.out.println("下菜心");
    }

    @Override
    public void pourSauce() {
        System.out.println("倒蒜蓉酱");
    }
}

