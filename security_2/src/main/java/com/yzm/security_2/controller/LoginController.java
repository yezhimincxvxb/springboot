package com.yzm.security_2.controller;

import com.yzm.security_2.constant.SysConstant;
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
    @GetMapping(SysConstant.LOGIN_PAGE_URL)
    public String login() {
        return "login";
    }

    @RequestMapping(SysConstant.SUCCESS_URL)
    public String success() {
        return "success";
    }

    @RequestMapping(SysConstant.FAIL_URL)
    public String fail() {
        return "fail";
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
     * 不需要登录
     */
    @RequestMapping("/toHome")
    public String toHome() {
        return "home";
    }

    /**
     * 需要登录，但不需要角色
     */
    @GetMapping("/toUser")
    @PreAuthorize("isAuthenticated()")
    public String toUser() {
        return "user";
    }

    /**
     * 需要登录，需要角色
     */
    @GetMapping("/toAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String toAdmin() {
        return "admin";
    }

}