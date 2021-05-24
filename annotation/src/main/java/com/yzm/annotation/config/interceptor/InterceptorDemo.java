package com.yzm.annotation.config.interceptor;


import com.yzm.annotation.config.interceptor.anno.Login;
import com.yzm.annotation.config.interceptor.anno.UserId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/interceptor")
public class InterceptorDemo {

    @GetMapping("/login")
    @Login(needAuth = false)
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("USER", "OK");
        session.setAttribute("USER_ID","998");
        session.setMaxInactiveInterval(60);
        return "login 登录！有效时间为60s";
    }

    @GetMapping("/look")
    @Login
    public String look() {
        return "look 随便看看";
    }

    @GetMapping("/getUserId")
    @Login
    public String getUserId(@UserId String userId) {
        return "look !" + userId;
    }

}

