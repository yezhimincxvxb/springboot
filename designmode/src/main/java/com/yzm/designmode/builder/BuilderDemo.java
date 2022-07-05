package com.yzm.designmode.builder;

public class BuilderDemo {

    public static void main(String[] args) {
        BUser user = BUser.builder("yzm","123456").nickName("一枝梅").height(170).weight(120).build();
        System.out.println("user = " + user);
        BUser user1 = new BUser.Builder("admin", "admin").nickName("管理员").height(180).weight(150).build();
        System.out.println("user1 = " + user1);
    }
}
