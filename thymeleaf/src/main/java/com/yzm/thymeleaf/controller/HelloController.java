package com.yzm.thymeleaf.controller;

import com.yzm.thymeleaf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class HelloController {

    @GetMapping("/ha")
    public String ha() {
        return "redirect:index.html";
    }

    @GetMapping("/hello")
    public ModelAndView hello(ModelAndView mav, HttpServletRequest request) {
        mav.setViewName("/hello");
        mav.addObject("title", "欢迎使用Thymeleaf！！！");

        User user = new User();
        user.setUsername("Yzm");
        request.getSession().setAttribute("user", user);
        mav.addObject("welcomeKey", "hello.welcome");

        mav.addObject("date", new Date());
        mav.addObject("calendar", Calendar.getInstance());
        return mav;
    }

    @GetMapping("/hello2")
    public String hello2(Map<String, Object> map) {
        User user = new User();
        user.setUserId(1);
        user.setUsername("AAA");
        user.setPassword("aaa");
        map.put("user", user);
        return "hello2";
    }

    @GetMapping("/hello3")
    public String hello3(Model map, Integer id, String name) {
        map.addAttribute("id", id);
        map.addAttribute("name", name);
        return "hello3";
    }

    @GetMapping("/order/info")
    @ResponseBody
    public String info(Integer id, String name) {
        return id + "：" + name;
    }

    @GetMapping("/hello4")
    public String hello4(ModelMap map) {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setUserId(1);
        user.setUsername("Yzm");
        user.setPassword("123");
        user.setBirthDay(new Date());
        user.setDesc("zzz");

        User user2 = new User();
        user2.setUserId(2);
        user2.setUsername("admin");
        user2.setPassword("12345");
        user2.setBirthDay(new Date());
        user2.setDesc("aaa");

        users.add(user);
        users.add(user2);

        Map<String, User> map1 = new HashMap<>();
        map1.put("user", user);
        map1.put("user2", user2);

        map.addAttribute("users", users);
        map.addAttribute("username", "yzm");
        map.addAttribute("studentmap", map1);
        return "hello4";
    }

    @GetMapping("/hello5")
    public String hello6(ModelMap map) {
        User user = new User();
        user.setUsername("yzm");
        map.addAttribute("title", "用户");
        map.addAttribute("user", user);
        map.addAttribute("name", "张三");
        map.addAttribute("age", 33);
        map.addAttribute("phone", 110);
        return "hello5";
    }

    @GetMapping("/user/info")
    public ModelAndView hello7(Model model) {
        User user = new User();
        user.setUserId(1);
        user.setUsername("yzm");
        user.setPassword("123");
        model.addAttribute("user", user);
        return new ModelAndView("/user/add", "userModel", model);
    }

    @PostMapping("/user/add")
    public String add(User user) {
        System.out.println("user = " + user);
        return "/user/success";
    }

    @GetMapping("/frag")
    public String frag(ModelMap map) {
        map.addAttribute("id", 1);
        map.addAttribute("name", "yzm");
        map.addAttribute("age", 25);
        return "/fragments/frag";
    }

    @GetMapping("/frag2")
    public String frag2() {
        return "/fragments/frag2";
    }

    @GetMapping("/frag3")
    public String frag3() {
        return "/fragments/frag3";
    }

    @GetMapping("/frag4")
    public String frag4() {
        return "/fragments/frag4";
    }

    @GetMapping("/frag5")
    public String frag5() {
        return "/fragments/frag5";
    }

}
