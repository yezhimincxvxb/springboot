package com.yzm.designmode.Q中介者模式Mediator;

/**
 * 中介者模式
 */
public class MediatorClient {
    public static void main(String[] args) {
        //中介
        ConcreteMediator mediator = new ConcreteMediator();

        //房主和租户
        HouseOwnerColleague houseOwner = new HouseOwnerColleague("张三", mediator);
        TenantColleague tenant = new TenantColleague("李四", mediator);

        mediator.setHouseOwner(houseOwner);
        mediator.setTenant(tenant);

        tenant.contact("你好！请问还有2房一厅？");
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
        System.out.println("房主:" + name + ",获得信息：" + message);
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
        System.out.println("租户:" + name + ",获得信息：" + message);
    }
}

/**
 * 具体中介者角色
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
        //如果是房主，则租房者获得信息
        if (person instanceof HouseOwnerColleague) {
            tenant.getMessage(message);
        } else {
            //反之则是获得房主信息
            houseOwner.getMessage(message);
        }
    }
}