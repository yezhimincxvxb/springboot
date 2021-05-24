package com.yzm.annotation.serialize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("serializeField")
public class SerializeController {

    /**
     * 显示User对象的所有字段
     */
    @GetMapping("demo00")
    public User demo00() {
        return new User(2L, "JJC", "123456", LocalDateTime.now());
    }

    /**
     * 显示User对象除了password之外的字段
     */
    @GetMapping("demo01")
    @SerializeField(clazz = User.class, excludes = {"password"})
    public User demo01() {
        return new User(2L, "JJC", "123456", LocalDateTime.now());
    }

    /**
     * 对象里嵌套其他对象的字段过滤
     * User对象不显示password、time
     * Address对象，不显示user字段
     * Dog对象，只显示name字段
     */
    @GetMapping("demo02")
    @SerializeFields({
            @SerializeField(clazz = User.class, excludes = {"password", "time"}),
            @SerializeField(clazz = Address.class, excludes = {"user"}),
            @SerializeField(clazz = Dog.class, includes = {"name"})
    })
    public User demo02() {
        User user = new User(1L, "JJC", "123456", LocalDateTime.now());
        Dog dog = new Dog(10, "小狗");
        Address address = new Address(1, "home", "school", null);
        Address address2 = new Address(11, "home2", "school2", null);
        user.setDog(dog);
        user.setAddress(address);
        user.setAddresses(Arrays.asList(address, address2));
        return user;
    }

    @GetMapping("demo03")
    @SerializeFields({
            @SerializeField(clazz = Address.class, includes = {"home", "user"}),
            @SerializeField(clazz = User.class, includes = {"id", "name"})
    })
    public Address demo03() {
        User user = new User(1L, "JJC", "123456", LocalDateTime.now());
        List<Address> addresses = new ArrayList<>();
        Address a1 = new Address(1, "home", "school", user);
        Address a2 = new Address(2, "home2", "school2", user);
        addresses.add(a1);
        addresses.add(a2);
        user.setAddresses(addresses);
        return a1;
    }
}
