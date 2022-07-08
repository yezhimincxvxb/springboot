package com.yzm.designmode.C建造者模式Builder;

public class BUser {

    //required：必填
    private final String username;
    private final String password;

    //optional：可选
    private final String nickName;
    private final int height;
    private final int weight;

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
