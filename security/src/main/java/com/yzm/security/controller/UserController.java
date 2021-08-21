package com.yzm.security.controller;

import com.yzm.security.utils.HttpResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("user")
public class UserController {

    @PreAuthorize("hasAuthority('select')")
    @GetMapping(value="/findAll")
    public HttpResult findAll() {
        return HttpResult.ok("the findAll service is called success.");
    }

    @PreAuthorize("hasAuthority('update')")
    @GetMapping(value="/update")
    public HttpResult update() {
        return HttpResult.ok("the edit service is called success.");
    }

    @PreAuthorize("hasAuthority('delete')")
    @GetMapping(value="/delete")
    public HttpResult delete() {
        return HttpResult.ok("the delete service is called success.");
    }

    @PreAuthorize("hasAuthority('save')")
    @GetMapping(value="/save")
    public HttpResult save() {
        return HttpResult.ok("the delete service is called success.");
    }

}
