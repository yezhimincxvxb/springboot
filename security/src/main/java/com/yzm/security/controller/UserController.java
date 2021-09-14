package com.yzm.security.controller;

import com.yzm.security.entity.User;
import com.yzm.security.service.UserService;
import com.yzm.security.utils.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 用户控制器
 */
@Controller
@RequestMapping("user")
@Api(value = "用户信息", tags = {"用户信息API"})
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/register")
    @ResponseBody
    public String register(String username, String password) {
        User user = User.builder().username(username).password(passwordEncoder.encode(password)).build();
        userService.save(user);
        return "注册成功";
    }

    // 需要认证才能访问
    @GetMapping("/hello")
    @PreAuthorize("isAuthenticated()")
    public String hello() {
        return "hello";
    }

    @GetMapping(value = "/select")
    @PreAuthorize("hasAuthority('SELECT')")
    @ResponseBody
    public HttpResult findAll() {
        return HttpResult.ok("the SELECT service is called success.");
    }

    @GetMapping(value = "/update")
    @PreAuthorize("hasAuthority('UPDATE')")
    @ResponseBody
    public HttpResult update() {
        return HttpResult.ok("the UPDATE service is called success.");
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('DELETE')")
    @ResponseBody
    public HttpResult delete() {
        return HttpResult.ok("the DELETE service is called success.");
    }

    @GetMapping(value = "/create")
    @PreAuthorize("hasAuthority('CREATE')")
    @ResponseBody
    public HttpResult save() {
        return HttpResult.ok("the CREATE service is called success.");
    }

}
