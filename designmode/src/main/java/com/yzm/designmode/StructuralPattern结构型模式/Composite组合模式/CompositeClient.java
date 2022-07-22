package com.yzm.designmode.StructuralPattern结构型模式.Composite组合模式;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式（Composite Pattern），又叫部分整体模式，是用于把一组相似的对象当作一个单一的对象。
 * 组合模式依据树形结构来组合对象，用来表示部分以及整体层次。这种类型的设计模式属于结构型模式，它创建了对象组的树形结构。
 * 这种模式创建了一个包含自己对象组的类。该类提供了修改相同对象组的方式。
 * <p>
 * 1、组合模式，就是在一个对象中包含其他对象，这些被包含的对象可能是终点对象（不再包含别的对象），也有可能是非终点对象（其内部还包含其他对象，或叫组对象），
 * 我们将对象称为节点，即一个根节点包含许多子节点，这些子节点有的不再包含子节点，而有的仍然包含子节点，以此类推。
 * 2、所谓组合模式，其实说的是对象包含对象的问题，通过组合的方式（在对象内部引用对象）来进行布局，我认为这种组合是区别于继承的，
 * 而另一层含义是指树形结构子节点的抽象（将叶子节点与树枝节点抽象为子节点），区别于普通的分别定义叶子节点与树枝节点的方式。
 * <p>
 * 意图：将对象组合成树形结构以表示"部分-整体"的层次结构。组合模式使得用户对单个对象和组合对象的使用具有一致性。
 * 主要解决：它在我们树型结构的问题中，模糊了简单元素和复杂元素的概念，客户程序可以像处理简单元素一样来处理复杂元素，从而使得客户程序与复杂元素的内部结构解耦。
 * 何时使用： 1、您想表示对象的部分-整体层次结构（树形结构）。 2、您希望用户忽略组合对象与单个对象的不同，用户将统一地使用组合结构中的所有对象。
 * 如何解决：树枝和叶子实现统一接口，树枝内部组合该接口。
 * 关键代码：树枝内部组合该接口，并且含有内部属性 List，里面放 Component。
 * 优点： 1、高层模块调用简单。 2、节点自由增加。
 * 缺点：在使用组合模式时，其叶子和树枝的声明都是实现类，而不是接口，违反了依赖倒置原则。
 * 使用场景：部分、整体场景，如树形菜单，文件、文件夹的管理。
 */
public class CompositeClient {
    public static void main(String[] args) {
        //创建一个文件类型
        AbstractFiles f1 = new Folder("父文件夹");
        //父文件夹内容
        AbstractFiles file0 = new TextFile("动漫大全.txt");
        AbstractFiles file1 = new ImageFile("海贼王.png");
        AbstractFiles file2 = new ImageFile("七龙珠.jpg");
        AbstractFiles file3 = new ImageFile("火影忍者.gif");
        Folder folder = new Folder("一级子文件夹");
        f1.add(file0);
        f1.add(file1);
        f1.add(file2);
        f1.add(file3);
        f1.add(folder);
        //一级子文件夹内容
        AbstractFiles file40 = new TextFile("主角人物.txt");
        AbstractFiles file4 = new ImageFile("鸣人.jpg");
        AbstractFiles file5 = new ImageFile("路飞.gif");
        Folder folder2 = new Folder("二级子文件夹");
        folder.add(file40);
        folder.add(file4);
        folder.add(file5);
        folder.add(folder2);
        //二级子文件夹内容
        AbstractFiles file6 = new ImageFile("恶魔果实.gif");
        AbstractFiles file7 = new ImageFile("忍术.gif");
        folder2.add(file6);
        folder2.add(file7);

        f1.killVirus();
    }

}

/**
 * 步骤 1
 * 文件接口：规范文件或文件夹行为
 */
abstract class AbstractFiles {
    abstract void add(AbstractFiles af);

    abstract void remove(AbstractFiles af);

    abstract AbstractFiles get(int i);

    public abstract void killVirus();
}

/**
 * 步骤 2
 * 文件夹类：可以添加文件、文件夹
 */
class Folder extends AbstractFiles {
    private final String name;
    private final List<AbstractFiles> list = new ArrayList<>();

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
        System.out.println("遍历文件夹 " + name + " 进行杀毒");
        //递归调用
        for (AbstractFiles o : list) {
            o.killVirus();
        }
    }
}

/**
 * 步骤 3
 * 抽象文件类
 */
abstract class AbstractFile extends AbstractFiles {
    /**
     * 不是文件夹，不支持添加、移除文件操作
     */
    @Override
    public final void add(AbstractFiles af) {
    }

    @Override
    public final void remove(AbstractFiles af) {
    }

    @Override
    public final AbstractFiles get(int i) {
        return null;
    }

    public abstract void killVirus();
}

/**
 * 步骤 4
 * 具体文件类：可以是文本文件、图片文件、音频文件、视频文件等
 */
class TextFile extends AbstractFile{

    private final String name;

    public TextFile(String name) {
        this.name = name;
    }

    @Override
    public void killVirus() {
        System.out.println("对文件 " + name + " 进行杀毒");
    }
}

class ImageFile extends AbstractFile{

    private final String name;

    public ImageFile(String name) {
        this.name = name;
    }

    @Override
    public void killVirus() {
        System.out.println("对文件 " + name + " 进行杀毒");
    }
}