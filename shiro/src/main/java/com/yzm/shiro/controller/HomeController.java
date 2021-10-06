package com.yzm.shiro.controller;

import com.yzm.shiro.config.PasswordHelper;
import com.yzm.shiro.entity.User;
import com.yzm.shiro.service.UserService;
import com.yzm.shiro.utils.HttpResult;
import com.yzm.shiro.utils.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String login() {
        return "login.html";
    }

    @GetMapping("home")
    @ResponseBody
    public Object home() {
        return "Here is home page";
    }

    @GetMapping("unauthorized")
    @ResponseBody
    public Object notRole() {
        return "Here is unauthorized page";
    }

    @GetMapping("register")
    @ResponseBody
    public Object register(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        PasswordHelper.encryptPassword(user);
        userService.save(user);
        return "SUCCESS";
    }

    @PostMapping("doLogin")
    @ResponseBody
    public Object doLogin(@RequestParam String username, @RequestParam String password, boolean rememberMe) {
        String token;
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            if (rememberMe) usernamePasswordToken.setRememberMe(true);
            Subject subject = SecurityUtils.getSubject();
            subject.login(usernamePasswordToken);

            Map<String, Object> map = new HashMap<>();
            map.put(JwtUtils.USERNAME, username);
            token = JwtUtils.generateToken(map);
        } catch (IncorrectCredentialsException ice) {
            return HttpResult.error("password error!");
        } catch (UnknownAccountException uae) {
            return HttpResult.error("username error!");
        }

        return HttpResult.ok((Object) token);
    }

    @RequiresAuthentication
    @GetMapping("info")
    @ResponseBody
    public Object info() {
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession().getAttribute("user");
    }

}
