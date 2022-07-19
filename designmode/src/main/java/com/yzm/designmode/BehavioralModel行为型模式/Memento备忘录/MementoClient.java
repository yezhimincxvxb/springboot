package com.yzm.designmode.BehavioralModel行为型模式.Memento备忘录;

import java.util.ArrayList;
import java.util.List;

/**
 * 备忘机制
 */
public class MementoClient {
    public static void main(String[] args) {
        //打BOSS之前：血、蓝全部满值
        Role role = new Role(100, 100);
        System.out.println("----------大战BOSS之前----------");
        role.display();

        //存档一
        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(role.saveMemento());

        //大战BOSS，快come Over了
        role.setBloodFlow(50);
        role.setMagicPoint(80);
        System.out.println("----------大战BOSS----------");
        role.display();

        //存档二
        caretaker.setMemento(role.saveMemento());

        //恢复存档
        System.out.println("----------恢复存档二----------");
        role.restoreMemento(caretaker.getMemento(1));
        role.display();
        System.out.println("----------恢复存档一----------");
        role.restoreMemento(caretaker.getMemento(0));
        role.display();

    }
}

/**
 * 游戏角色
 */
class Role{
    private int bloodFlow;
    private int magicPoint;

    public Role(int bloodFlow,int magicPoint){
        this.bloodFlow = bloodFlow;
        this.magicPoint = magicPoint;
    }

    public int getBloodFlow() {
        return bloodFlow;
    }

    public void setBloodFlow(int bloodFlow) {
        this.bloodFlow = bloodFlow;
    }

    public int getMagicPoint() {
        return magicPoint;
    }

    public void setMagicPoint(int magicPoint) {
        this.magicPoint = magicPoint;
    }

    /**
     * @desc 展示角色当前状态
     */
    public void display(){
        System.out.println("用户当前状态:");
        System.out.println("血量:" + getBloodFlow() + ";蓝量:" + getMagicPoint());
    }

    /**
     * @desc 保持存档、当前状态
     */
    public Memento saveMemento(){
        return new Memento(getBloodFlow(), getMagicPoint());
    }

    /**
     * @desc 恢复存档
     */
    public void restoreMemento(Memento memento){
        this.bloodFlow = memento.getBloodFlow();
        this.magicPoint = memento.getMagicPoint();
    }
}

class Memento {
    private int bloodFlow;
    private int magicPoint;

    public Memento(int bloodFlow,int magicPoint){
        this.bloodFlow = bloodFlow;
        this.magicPoint = magicPoint;
    }

    public int getBloodFlow() {
        return bloodFlow;
    }

    public void setBloodFlow(int bloodFlow) {
        this.bloodFlow = bloodFlow;
    }

    public int getMagicPoint() {
        return magicPoint;
    }

    public void setMagicPoint(int magicPoint) {
        this.magicPoint = magicPoint;
    }
}

class Caretaker {
    private final List<Memento> mementos = new ArrayList<>();

    public Memento getMemento(int index) {
        return mementos.get(index);
    }

    public void setMemento(Memento memento) {
        this.mementos.add(memento);
    }
}