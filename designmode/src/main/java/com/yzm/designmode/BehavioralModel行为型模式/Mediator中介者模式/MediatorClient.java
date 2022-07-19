package com.yzm.designmode.BehavioralModel行为型模式.Mediator中介者模式;

/**
 * 中介者模式
 * 中介者模式（Mediator Pattern）是用来降低多个对象和类之间的通信复杂性。
 * 这种模式提供了一个中介类，该类通常处理不同类之间的通信，并支持松耦合，使代码易于维护。中介者模式属于行为型模式。
 * <p>
 * 意图：用一个中介对象来封装一系列的对象交互，中介者使各对象不需要显式地相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互。
 * 主要解决：对象与对象之间存在大量的关联关系，这样势必会导致系统的结构变得很复杂，同时若一个对象发生改变，我们也需要跟踪与之相关联的对象，同时做出相应的处理。
 * 何时使用：多个类相互耦合，形成了网状结构。
 * 如何解决：将上述网状结构分离为星型结构。
 * 关键代码：对象 Colleague 之间的通信封装到一个类中单独处理。
 * 应用实例： 例如QQ游戏平台，聊天室、QQ群、短信平台和房产中介
 * 优点： 1、降低了类的复杂度，将一对多转化成了一对一。 2、各个类之间的解耦。 3、符合迪米特原则。
 * 缺点：中介者会庞大，变得复杂难以维护。
 * 使用场景： 1、系统中对象之间存在比较复杂的引用关系，导致它们之间的依赖关系结构混乱而且难以复用该对象。 2、想通过一个中间类来封装多个类中的行为，而又不想生成太多的子类。
 * 注意事项：不应当在职责混乱的时候使用。
 */
public class MediatorClient {
    public static void main(String[] args) throws InterruptedException {
        //中介
        ConcreteMediator mediator = new ConcreteMediator();

        //房主和租户
        HouseOwnerColleague houseOwner = new HouseOwnerColleague("张三", mediator);
        TenantColleague tenant = new TenantColleague("李四", mediator);

        mediator.setHouseOwner(houseOwner);
        mediator.setTenant(tenant);

        tenant.contact("你好！请问还有2房一厅？");
        System.out.println();
        Thread.sleep(1000);
        houseOwner.contact("有的有的，租金在2000-2800，你能接收么？");
    }
}

/**
 * 中介者角色
 */
abstract class IMediator {
    //申明一个联络方法
    public abstract void contact(String message, Person person);
}

/**
 * 合作者角色超类
 */
abstract class Person {
    protected String name;
    protected IMediator mediator;

    public Person(String name, IMediator mediator) {
        this.mediator = mediator;
        this.name = name;
    }

    public abstract void getMessage(String message);

    public abstract void contact(String message);
}

/**
 * 房主合作者，需与中介者建立联系
 */
class HouseOwnerColleague extends Person {

    public HouseOwnerColleague(String name, IMediator mediator) {
        super(name, mediator);
    }

    @Override
    public void contact(String message) {
        mediator.contact(message, this);
    }

    @Override
    public void getMessage(String message) {
        System.out.println("房主:" + name + ",收到信息：" + message);
    }
}

/**
 * 租户合作者，需与中介者建立联系
 */
class TenantColleague extends Person {

    public TenantColleague(String name, IMediator mediator) {
        super(name, mediator);
    }

    @Override
    public void contact(String message) {
        mediator.contact(message, this);
    }

    @Override
    public void getMessage(String message) {
        System.out.println("租户: " + name + " ,收到信息：" + message);
    }
}

/**
 * 具体中介者角色
 * 需要注入交互对象的的引用
 */
class ConcreteMediator extends IMediator {
    private HouseOwnerColleague houseOwner;
    private TenantColleague tenant;

    public HouseOwnerColleague getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(HouseOwnerColleague houseOwner) {
        this.houseOwner = houseOwner;
    }

    public TenantColleague getTenant() {
        return tenant;
    }

    public void setTenant(TenantColleague tenant) {
        this.tenant = tenant;
    }

    @Override
    public void contact(String message, Person person) {
        System.err.println(person.name + " 发送消息：" + message);

        //如果是房主，则租房者获得信息
        if (person instanceof HouseOwnerColleague) {
            tenant.getMessage(message);
        } else {
            //反之则是获得房主信息
            houseOwner.getMessage(message);
        }
    }
}