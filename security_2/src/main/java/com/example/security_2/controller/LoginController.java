package com.example.security_2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录控制器
 */
@Controller
public class LoginController {

    /**
     * 跳转到home.html页面（不需要登录）
     */
    @GetMapping("/user/login")
    public String authLogin() {
        return "login";
    }

    @RequestMapping("/loginFail")
    public String loginFail() {
        return "loginFail";
    }

    /**
     * 跳转到home.html页面（不需要登录）
     */
    @RequestMapping("/toHome")
    public String toHome() {
        return "home";
    }

    /**
     * 用户认证成功之后，可以通过@AuthenticationPrincipal注解来获取认证用户信息
     */
    @GetMapping("/getUserDetails")
    @ResponseBody
    public Object getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

    /**
     * 跳转到admin.html页面（需要登录，且需要ROLE_ADMIN角色）
     */
    @GetMapping("/toAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String toAdmin() {
        return "admin";
    }

    /**
     * 跳转到user.html页面（需要登录，但不需要角色）
     * 注意：虽然WebSecurityConfig中配置了/toUser不需要登录，但是这里配置的权限更小，因此，/toUser以这里的配置为准
     */
    @GetMapping("/toUser")
    @PreAuthorize("isAuthenticated()")
    public String toUser() {
        return "user";
    }

}