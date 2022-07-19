package com.yzm.designmode.StructuralPattern结构型模式.Facade外观模式;

/**
 * 外观模式，又称门面模式
 * <p>
 * 外观模式的作用：
 * 松散耦合，外观模式松散了客户端与子系统的耦合关系，让子系统内部的模块能更容易扩展和维护。
 * 简单易用，外观模式让子系统更加易用，客户端不再需要了解子系统内部的实现，也不需要跟众多子系统内部的模块进行交互，只需要跟门面类交互就可以了。
 * 更好的划分访问层次-通过合理使用 Facade，可以帮助我们更好地划分访问的层次。有些方法是对系统外的，有些方法是系统内部使用的。把需要暴露给外部的功能集中到门面中，这样既方便客户端使用，也很好地隐藏了内部的细节。
 * <p>
 * 外观模式的角色：
 * SubSystem: 子系统角色。表示一个系统的子系统或模块。
 * Facade: 外观角色，客户端通过操作外观角色从而达到控制子系统角色的目的。
 * 对于客户端来说，外观角色好比一道屏障，对客户端屏蔽了子系统的具体实现。
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