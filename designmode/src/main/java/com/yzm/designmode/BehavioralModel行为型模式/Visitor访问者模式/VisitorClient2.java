package com.yzm.designmode.BehavioralModel行为型模式.Visitor访问者模式;

import java.util.ArrayList;
import java.util.List;

public class VisitorClient2 {
    public static void main(String[] args) {
        //账簿
        AccountBook accountBook = new AccountBook();
        //添加两条收入
        accountBook.addBill(new IncomeBill(10000, "卖商品"));
        accountBook.addBill(new IncomeBill(12000, "卖广告位"));
        //添加两条支出
        accountBook.addBill(new ConsumeBill(1000, "工资"));
        accountBook.addBill(new ConsumeBill(2000, "材料费"));

        //访问者
        IVisitor boss = new BossVisitor();
        IVisitor cpa = new CPAVisitor();

        //两个访问者分别访问账本
        accountBook.show(cpa);
        accountBook.show(boss);
        System.out.println("老板查看一共收入多少，数目是：" + ((BossVisitor) boss).getTotalIncome());
        System.out.println("老板查看一共花费多少，数目是：" + ((BossVisitor) boss).getTotalConsume());
    }
}

//账单接口（相当于Element）
abstract class Bill {
    double amount;
    String item;

    public Bill(double amount, String item) {
        this.amount = amount;
        this.item = item;
    }

    public double getAmount() {
        return amount;
    }

    public String getItem() {
        return item;
    }

    abstract void accept(IVisitor viewer);
}

//消费的单子
class ConsumeBill extends Bill {

    public ConsumeBill(double amount, String item) {
        super(amount, item);
    }

    public void accept(IVisitor viewer) {
        viewer.visit(this);
    }
}

//收入单子
class IncomeBill extends Bill {

    public IncomeBill(double amount, String item) {
        super(amount, item);
    }

    public void accept(IVisitor viewer) {
        viewer.visit(this);
    }
}

//账单查看者接口（相当于Visitor）
interface IVisitor {

    //查看消费的单子
    void visit(ConsumeBill bill);

    //查看收入的单子
    void visit(IncomeBill bill);

}

//老板类，查看账本的类之一
class BossVisitor implements IVisitor {

    private double totalIncome;
    private double totalConsume;

    //老板只关注一共花了多少钱以及一共收入多少钱，其余并不关心
    public void visit(ConsumeBill bill) {
        totalConsume += bill.getAmount();
    }

    public void visit(IncomeBill bill) {
        totalIncome += bill.getAmount();
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalConsume() {
        return totalConsume;
    }

}

//注册会计师类，查看账本的类之一
class CPAVisitor implements IVisitor {

    //注会在看账本时，如果是支出，则如果支出是工资，则需要看应该交的税交了没
    public void visit(ConsumeBill bill) {
        if (bill.getItem().equals("工资")) {
            System.out.println("支出账单(" + bill.getItem() + ")：会计查看是否已缴纳个人所得税...");
        }
    }

    //如果是收入，则所有的收入都要交税
    public void visit(IncomeBill bill) {
        System.out.println("收入账单(" + bill.getItem() + ")：会计查看该笔收入是否已交税...");
    }

}

//账本类（相当于ObjectStructure）
class AccountBook {
    //单子列表
    private final List<Bill> billList = new ArrayList<>();

    //添加单子
    public void addBill(Bill bill) {
        billList.add(bill);
    }

    //供账本的查看者查看账本
    public void show(IVisitor viewer) {
        for (Bill bill : billList) {
            bill.accept(viewer);
        }
    }
}
