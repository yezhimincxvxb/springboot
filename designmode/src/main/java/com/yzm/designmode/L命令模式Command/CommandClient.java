package com.yzm.designmode.L命令模式Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Command：定义命令的接口，声明执行的方法。
 * ConcreteCommand：命令接口实现对象，是“虚”的实现；通常会持有接收者，并调用接收者的功能来完成命令要执行的操作。
 * Receiver：接收者，真正执行命令的对象。任何类都可能成为一个接收者，只要它能够实现命令要求实现的相应功能。
 * Invoker：要求命令对象执行请求，通常会持有命令对象，可以持有很多的命令对象。这个是客户端真正触发命令并要求命令执行相应操作的地方，也就是说相当于使用命令对象的入口。
 * Client：创建具体的命令对象，并且设置命令对象的接收者。注意这个不是我们常规意义上的客户端，而是在组装命令对象和接收者，或许，把这个Client称为装配者会更好理解，因为真正使用命令的客户端是从Invoker来触发执行。
 *
 */
public class CommandClient {
    public static void main(String[] args) {
        ICommand command = new ConcreteCommand(new Receiver("y1"));
        ICommand command2 = new ConcreteCommand(new Receiver("y2"));

        Invoker invoker = new Invoker(command);
        invoker.add(command2);
        invoker.doInvokerAction();
    }

}

/**
 * 命令角色
 */
interface ICommand {
    void execute();

    void undo();
}

/**
 * 具体命令角色
 */
class ConcreteCommand implements ICommand {
    //接收者的引用
    private final Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        //将命令的执行委托给接收者
        receiver.doAction();
    }

    @Override
    public void undo() {
        receiver.undo();
    }

}

/**
 * 接收者角色
 */
class Receiver {
    private String name;

    public Receiver(String name) {
        this.name = name;
    }

    //接收者真正执行命令
    public void doAction() {
        System.out.println("执行" + name);
    }

    //接收者真正执行命令
    public void undo() {
        System.out.println("撤销" + name);
    }
}

/**
 * 请求者（执行者）角色
 */
class Invoker {
    //持有命令对象的引用，将动作委托给命令对象执行
    private List<ICommand> commands = new ArrayList<>();

    public Invoker(ICommand command) {
        this.commands.add(command);
    }

    public Invoker(List<ICommand> commands) {
        this.commands = commands;
    }

    public boolean add(ICommand command) {
        return this.commands.add(command);
    }

    public void doInvokerAction() {
        for (ICommand command : this.commands) {
            command.execute();
        }
    }

    //接收者真正执行命令
    public void undo() {
        for (ICommand command : this.commands) {
            command.undo();
        }
    }
}