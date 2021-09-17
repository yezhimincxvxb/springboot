package com.yzm.security.controller;

import com.yzm.security.entity.User;
import com.yzm.security.service.UserService;
import com.yzm.security.utils.HttpResult;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("/login")
    @ResponseBody
    public String login(String username, String password) {
        User user = User.builder().username(username).password(passwordEncoder.encode(password)).build();
        userService.save(user);
        return "";
    }

    @GetMapping("/me")
    @ResponseBody
    public Object me(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }
/*
    public Object me(Authentication authentication) {
        return authentication;
    }
*/

    // 需要认证才能访问
    @GetMapping("/hello")
    @PreAuthorize("isAuthenticated()")
    public String hello() {
        return "hello";
    }

    @GetMapping(value = "/toUser")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ResponseBody
    public HttpResult toUser() {
        return HttpResult.ok("has user role");
    }

    @GetMapping(value = "/toAdmin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseBody
    public HttpResult toAdmin() {
        return HttpResult.ok("has admin role");
    }

    @GetMapping(value = "/adminSelect")
    @PreAuthorize("hasPermission('/admin','select')")
    @ResponseBody
    public HttpResult adminSelect() {
        return HttpResult.ok("has adminSelect permission");
    }

    @GetMapping(value = "/adminDelete")
    @PreAuthorize("hasPermission('/admin','delete')")
    @ResponseBody
    public HttpResult adminDelete() {
        return HttpResult.ok("has adminDelete permission");
    }

    @GetMapping(value = "/userSelect")
    @PreAuthorize("hasPermission('/user','select')")
    @ResponseBody
    public HttpResult userSelect() {
        return HttpResult.ok("has userSelect permission");
    }

    @GetMapping(value = "/userDelete")
    @PreAuthorize("hasPermission('/user','delete')")
    @ResponseBody
    public HttpResult userDelete() {
        return HttpResult.ok("has userDelete permission");
    }

}
