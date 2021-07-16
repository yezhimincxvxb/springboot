package com.yzm.logback.controller;


import com.yzm.logback.entity.User;
import com.yzm.logback.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author Yzm
 * @since 2021-07-16
 */
@Slf4j
@RestController
@RequestMapping("//user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/logger")
    public void logger() {
        log.trace("trace 级别打印");
        log.debug("debug 级别打印");
        log.info("info 级别打印");
        log.warn("warn 级别打印");
        log.error("error 级别打印");
    }

    @GetMapping("/list")
    public List<User> listUser() {
        return userService.listUser();
    }

    @GetMapping("/list2")
    public List<User> listUser2() {
        return userService.list();
    }

    @GetMapping("/error")
    public void error() {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            log.error("程序报错! ", e);
        }
    }

}
