package com.yzm.validation.controller;

import com.yzm.validation.annotation.Insert;
import com.yzm.validation.annotation.IsPhone;
import com.yzm.validation.annotation.Update;
import com.yzm.validation.entity.ValidGroup;
import com.yzm.validation.entity.ValidUser;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

@RestController
@RequestMapping("/validation")
@Validated
public class ValidationController {

    @GetMapping(path = "/test01/{name}")
    public void test01(@PathVariable(value = "name") String name) {
        System.out.println("从请求路径获取参数值");
        System.out.println("test01：" + name);
    }

    @GetMapping(path = "/test01_2")
    public void test01_2(@RequestParam(name = "name") String name, @RequestParam String password) {
        System.out.println("参数name跟password都是必传的，不传报错");
        System.out.println("test01_2：name=" + name + "password=" + password);
    }

    @GetMapping(path = "/test01_3")
    public void test01_3(@RequestParam(value = "name", required = false, defaultValue = "aaa") String name) {
        System.out.println("请求name不是必传，不传有默认值");
        System.out.println("test01_3：" + name);
    }

    @GetMapping(path = "/test02")
    public void test02(@NotBlank(message = "姓名不为空") String name) {
        System.out.println("在请求参数前使用约束注解，需要在controller上加@Validated才会生效");
        System.out.println(name);
    }

    @GetMapping(path = "/test02_2")
    public void test02_2(@NotNull @Range(min = 1, max = 10, message = "年龄取值范围在1~10") Integer age) {
        System.out.println(age);
    }

    @PostMapping(path = "/test03")
    public void test03(@RequestBody ValidUser user) {
        System.out.println("请求参数是对象时，不加@Validated，是不会校验对象属性上的约束的");
        System.out.println("user = " + user);
    }

    @PostMapping(path = "/test03_2")
    public void test03_2(@RequestBody @Validated ValidUser user) {
        System.out.println("加了@Validated");
        System.out.println("user = " + user);
    }

    @GetMapping(path = "/test04")
    public void test04(@RequestParam @Pattern(regexp = "^1\\d{10}$", message = "手机格式错误") String phone) {
        System.out.println("phone = " + phone);
    }

    @GetMapping(path = "/test04_2")
    public void test04_2(@RequestParam @IsPhone String phone) {
        System.out.println("自定义的手机号码验证");
        System.out.println("phone = " + phone);
    }

    @PostMapping(path = "/test05")
    public void test05(@RequestBody @Validated(value = {Insert.class}) ValidGroup group) {
        //指定Insert分组，Insert继承Default
        //所以ValidGroup对象的4个属性约束都生效
        System.out.println("group = " + group);
    }

    @PostMapping(path = "/test05_2")
    public void test05_2(@RequestBody @Validated(value = {Update.class}) ValidGroup group) {
        //指定Update分组，只有username属性约束有效
        System.out.println("group = " + group);
    }

    @PostMapping(path = "/test05_3")
    public void test05_3(@RequestBody @Validated(value = {Default.class}) ValidGroup group) {
        //指定Default分组，id跟password属性约束有效
        System.out.println("group = " + group);
    }


}
