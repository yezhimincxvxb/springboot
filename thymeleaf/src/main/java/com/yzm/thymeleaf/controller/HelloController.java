package com.yzm.thymeleaf.controller;

import com.yzm.thymeleaf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class HelloController {

    @GetMapping("/ha")
    public String ha() {
        return "redirect:hello.html";
    }

    @GetMapping("/hello")
    public ModelAndView hello(ModelAndView mav) {
        mav.setViewName("/hello");
        mav.addObject("title", "欢迎使用Thymeleaf！！！");
        return mav;
    }

    @GetMapping("/format")
    public String format(ModelMap map) {
        map.addAttribute("date", new Date());
        map.addAttribute("calendar", Calendar.getInstance());
        return "/hello";
    }

    @GetMapping("/hello2")
    public String hello2(ModelMap map, HttpServletRequest request) {
        User user = new User();
        user.setUsername("Yzm");
        request.getSession().setAttribute("user", user);
        map.addAttribute("welcomeMsgKey", "home.welcome2");
        return "/hello2";
    }

    @GetMapping("/hello3")
    public String hello3(ModelMap map) {
        User user = new User();
        user.setUserId(1);
        user.setUsername("AAA");
        user.setPassword("aaa");
        map.addAttribute("user", user);
        return "/hello3";
    }

    @GetMapping("/hello4")
    public String hello4(ModelMap map, Integer id, String name) {
        map.addAttribute("id", id);
        map.addAttribute("name", name);
        return "/hello4";
    }

    @GetMapping("/order/info")
    @ResponseBody
    public String info(Integer id, String name) {
        return id + "：" + name;
    }

    @GetMapping("/users")
    public String users(ModelMap map) {
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

        map.addAttribute("users", users);
        return "/user";
    }

}
