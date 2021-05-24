package com.yzm.annotation.config.filter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/filter")
public class FilterDemo {

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("USER", "LOGIN");
        session.setMaxInactiveInterval(60);
        System.out.println("登录成功，过期时间60s");
        return "登录成功，过期时间60s";
    }

    @GetMapping("/look")
    public String look(@SessionAttribute("USER") String user) {
        return user + "！随便看看";
    }

}
