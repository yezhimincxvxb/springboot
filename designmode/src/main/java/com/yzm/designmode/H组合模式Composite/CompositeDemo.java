package com.yzm.designmode.H组合模式Composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式
 * Composite 模式定义:
 * 将对象以树形结构组织起来,以达成“部分－整体” 的层次结构，使得客户端对单个对象和组合对象的使用具有一致性.
 */
public class CompositeDemo {
    public static void main(String[] args) {
        //创建一个文件类型
        AbstractFiles f1 = new Folder("主文件夹");
        //创建文件
        AbstractFiles file1= new ImageFile("海贼王.png");
        AbstractFiles file2= new ImageFile("七龙珠.jpg");
        AbstractFiles file3= new ImageFile("火影忍者.gif");
        f1.add(file1);
        f1.add(file2);
        f1.add(file3);
        Folder folder = new Folder("一级文件夹");
        AbstractFiles file4= new ImageFile("鸣人.jpg");
        AbstractFiles file5= new ImageFile("路飞.gif");
        folder.add(file4);
        folder.add(file5);
        Folder folder2 = new Folder("二级文件夹");
        AbstractFiles file6= new ImageFile("恶魔果实.gif");
        AbstractFiles file7= new ImageFile("忍术.gif");
        folder2.add(file6);
        folder2.add(file7);
        folder.add(folder2);
        f1.add(folder);
        f1.killVirus();
    }

}


abstract class AbstractFiles {
    public abstract void add(AbstractFiles af);

    public abstract void remove(AbstractFiles af);

    public abstract AbstractFiles get(int i);

    public abstract void killVirus();
}

class ImageFile extends AbstractFiles {
    private String name;

    public ImageFile(String name) {
        this.name = name;
    }


    @Override
    public void add(AbstractFiles af) {
        //不是子容器
        System.out.println("不支持该方法");
    }

    @Override
    public void remove(AbstractFiles af) {
        System.out.println("不支持该方法");
    }

    @Override
    public AbstractFiles get(int i) {
        System.out.println("不支持该方法");
        return null;
    }

    @Override
    public void killVirus() {
        System.out.println("开始进行--" + name + "--文件杀毒");
    }
}

class Folder extends AbstractFiles {
    //文件夹类，所有的都可以用
    private List<AbstractFiles> list = new ArrayList<AbstractFiles>();
    private String name;

    public Folder(String name) {
        this.name = name;
    }

    @Override
    public void add(AbstractFiles af) {
        list.add(af);
    }

    @Override
    public void remove(AbstractFiles af) {
        list.remove(af);
    }

    @Override
    public AbstractFiles get(int i) {
        return list.get(i);
    }

    @Override
    public void killVirus() {
        System.out.println("对文件夹" + name + "进行杀毒");
        //递归调用
        for (AbstractFiles o : list) {
            o.killVirus();
        }
    }
}