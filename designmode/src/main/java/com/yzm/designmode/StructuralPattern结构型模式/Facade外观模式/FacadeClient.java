package com.yzm.designmode.StructuralPattern结构型模式.Facade外观模式;

/**
 * 外观模式（Facade Pattern）隐藏系统的复杂性，并向客户端提供了一个客户端可以访问系统的接口。
 * 这种类型的设计模式属于结构型模式，它向现有的系统添加一个接口，来隐藏系统的复杂性。
 *
 * 意图：为子系统中的一组接口提供一个一致的界面，外观模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。
 * 主要解决：降低访问复杂系统的内部子系统时的复杂度，简化客户端之间的接口。
 * 何时使用： 1、客户端不需要知道系统内部的复杂联系，整个系统只需提供一个"接待员"即可。 2、定义系统的入口。
 * 如何解决：客户端不与系统耦合，外观类与系统耦合。
 * 关键代码：在客户端和复杂系统之间再加一层，这一层将调用顺序、依赖关系等处理好。
 * 应用实例： 1、去医院看病，可能要去挂号、门诊、划价、取药，让患者或患者家属觉得很复杂，如果有提供接待人员，只让接待人员来处理，就很方便。 2、JAVA 的三层开发模式。
 * 优点： 1、减少系统相互依赖。 2、提高灵活性。 3、提高了安全性。
 * 缺点：不符合开闭原则，如果要改东西很麻烦，继承重写都不合适。
 * 使用场景： 1、为复杂的模块或子系统提供外界访问的模块。 2、子系统相对独立。 3、预防低水平人员带来的风险。
 * 注意事项：在层次化结构中，可以使用外观模式定义系统中每一层的入口。
 */
public class FacadeClient {
    public static void main(String[] args) {
        //客户端，只知道Computer这个外观角色，而不知道其内部具体的细节
        Computer computer = new Computer();
        computer.startup();
        System.out.println();
        computer.shutdown();
    }
}

/**
 * 场景：
 * 假设一台电脑，它包含了 CPU（处理器），Memory（内存） ，Disk（硬盘）这几个部件，若想要启动电脑，则先后必须启动 CPU、Memory、Disk。关闭也是如此。
 * 但是实际上我们在电脑开/关机时根本不需要去操作这些组件，因为电脑已经帮我们都处理好了，并隐藏了这些东西。
 * 这些组件好比子系统角色，而电脑就是一个外观角色。
 */

/**
 * SubSystem 子系统角色
 */
class CPU {
    public void startup() {
        System.out.println("cpu startup!");
    }

    public void shutdown() {
        System.out.println("cpu shutdown!");
    }
}

class Memory {
    public void startup() {
        System.out.println("memory startup!");
    }

    public void shutdown() {
        System.out.println("memory shutdown!");
    }
}

class Disk {
    public void startup() {
        System.out.println("disk startup!");
    }

    public void shutdown() {
        System.out.println("disk shutdown!");
    }
}

/**
 * Facade 外观角色
 */
class Computer {
    private final CPU cpu;
    private final Memory memory;
    private final Disk disk;

    public Computer() {
        cpu = new CPU();
        memory = new Memory();
        disk = new Disk();
    }

    public void startup() {
        System.out.println("start the computer!");
        cpu.startup();
        memory.startup();
        disk.startup();
        System.out.println("start computer finished!");
    }

    public void shutdown() {
        System.out.println("begin to close the computer!");
        cpu.shutdown();
        memory.shutdown();
        disk.shutdown();
        System.out.println("computer closed!");
    }
}