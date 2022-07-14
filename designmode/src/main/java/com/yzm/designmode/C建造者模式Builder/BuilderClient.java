package com.yzm.designmode.C建造者模式Builder;

/**
 * Builder 模式定义:
 * 将一个复杂对象的构建与它的表示分离,使得同样的构建过程可以创建不同的表示.
 */
public class BuilderClient {

    public static void main(String[] args) {
        //通过调用static静态方法创建内部类实例
        BUser user = BUser.builder("yzm","123456").nickName("一枝梅").height(170).weight(120).build();
        System.out.println("user = " + user);
        //通过new 外部类.内部类方式创建内部类实例
        BUser user1 = new BUser.Builder("admin", "admin").nickName("管理员").height(180).weight(150).build();
        System.out.println("user1 = " + user1);
    }
}

class BUser {

    //required：必填
    private final String username;
    private final String password;

    //optional：可选
    private final String nickName;
    private final int height;
    private final int weight;

    //不对外提供创建实例对象，必须通过建造者创建
    private BUser(Builder builder){
        this.username = builder.username;
        this.password = builder.password;

        this.nickName = builder.nickName;
        this.height = builder.height;
        this.weight = builder.weight;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "BUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }

    public static Builder builder(String username, String password) {
        return new Builder(username, password);
    }

    public static class Builder{
        private final String username;
        private final String password;

        private String nickName;
        private int height;
        private int weight;

        //必填参数通过构造器强制要求赋值
        public Builder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public Builder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public BUser build(){
            return new BUser(this);
        }
    }

}
