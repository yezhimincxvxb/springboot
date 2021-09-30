package com.yzm.shiro.controller;

import com.yzm.shiro.config.PasswordHelper;
import com.yzm.shiro.entity.User;
import com.yzm.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @PostMapping("doLogin")
    @ResponseBody
    public Object doLogin(@RequestParam String username, @RequestParam String password, boolean rememberMe) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            if (rememberMe) token.setRememberMe(true);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            return "password error!";
        } catch (UnknownAccountException uae) {
            return "username error!";
        }
        return "SUCCESS";
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<Void> logout() {
        SecurityUtils.getSubject().logout();
        return ResponseEntity.ok().build();
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

}
