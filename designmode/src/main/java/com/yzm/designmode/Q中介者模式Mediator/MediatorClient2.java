package com.yzm.designmode.Q中介者模式Mediator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 模拟聊天室
 */
public class MediatorClient2 {
    public static void main(String[] args) throws InterruptedException {
        //创建聊天平台
        IMediator2 mediator = new ConcreteMediator2();

        User lord = new User("群主", -1, mediator);
        User admin = new User("管理员", 0, mediator);
        User one = new User("水友1号", 1, mediator);
        User two = new User("水友2号", 1, mediator);
        User three = new User("水友3号", 1, mediator);

        mediator.addUser(lord);
        mediator.addUser(admin);
        mediator.addUser(one);
        mediator.addUser(two);
        mediator.addUser(three);

        admin.sendMessage("大家好！上一个群炸了，群主可开心了");
        System.out.println();
        Thread.sleep(1000);

        lord.sendMessage(".....");
        System.out.println();
        Thread.sleep(1000);

        one.sendMessage("秋名山老司机来此报道");
        System.out.println();
        Thread.sleep(1000);

        two.sendMessage("老司机带带我");
        System.out.println();
        Thread.sleep(1000);

        mediator.setMute(lord, true);
        System.out.println();
        Thread.sleep(1000);

        mediator.setMute(admin, false);
        System.out.println();
        Thread.sleep(1000);

        admin.sendMessage("嘿嘿嘿！");
        System.out.println();
        Thread.sleep(1000);

        three.sendMessage("我有车牌号。嘿嘿，你懂的！");
        System.out.println();
        Thread.sleep(1000);

        one.setAitUser(three);
        one.sendMessage("嘿嘿，哥们！我帮我朋友问问");
    }
}

/**
 * 聊天平台，扮演中介者角色
 */
interface IMediator2 {
    //发送消息
    void sendMessage(String message, User user);

    //入群
    void addUser(User user);

    //禁言
    void setMute(User user, boolean mute);
}

/**
 * 水友，扮演交互对象
 */
class User {
    protected String name;
    //0管理员、1普通水友
    protected Integer identity;
    protected IMediator2 mediator;

    //@User
    protected User aitUser;

    public User(String name, Integer identity, IMediator2 mediator) {
        this.mediator = mediator;
        this.name = name;
        this.identity = identity;
    }

    public void setAitUser(User user) {
        this.aitUser = user;
    }

    public void sendMessage(String message) {
        mediator.sendMessage(message, this);
    }

    public void getMessage(String message) {
        String format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        System.out.println(format + " ==> (" + name + ") 收到信息：" + message);
    }
}

/**
 * 中介者具体实现
 * 需要注入交互对象的的引用
 */
class ConcreteMediator2 implements IMediator2 {
    private final List<User> users = new ArrayList<>();
    private boolean mute = false;

    @Override
    public void addUser(User user) {
        this.users.add(user);
    }

    @Override
    public void setMute(User user, boolean mute) {
        if (user.identity <= 0) {
            this.mute = mute;
            System.err.println(mute ? user.name + "开启全体禁言" : user.name + "关闭全体禁言");
        } else {
            System.err.println("没有权限");
        }
    }

    @Override
    public void sendMessage(String message, User user) {
        if (user.identity == 1 && mute) {
            System.err.println("被禁言了，无法发送信息");
            return;
        }

        String format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        System.err.println(format + "==> (" + user.name + ") 发送信息：" + message);

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (user.aitUser != null) {
            //艾特某人
            user.aitUser.getMessage(message);
        } else {
            //群发
            for (User user1 : users) {
                if (user == user1) {
                    continue;
                }

                user1.getMessage(message);
            }
        }
    }
}