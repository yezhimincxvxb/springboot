package com.yzm.security.controller;

import com.yzm.security.utils.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("user")
@Api(value = "用户信息", tags = {"用户信息API"})
public class UserController {

    @ApiOperation(value = "findAll", notes = "findAll")
    @GetMapping(value="/findAll")
    @PreAuthorize("hasAuthority('select')")
    public HttpResult findAll() {
        return HttpResult.ok("the findAll service is called success.");
    }

    @ApiOperation(value = "update", notes = "update")
    @GetMapping(value="/update")
    @PreAuthorize("hasAuthority('update')")
    public HttpResult update() {
        return HttpResult.ok("the edit service is called success.");
    }

    @ApiOperation(value = "delete", notes = "delete")
    @GetMapping(value="/delete")
    @PreAuthorize("hasAuthority('delete')")
    public HttpResult delete() {
        return HttpResult.ok("the delete service is called success.");
    }

    @ApiOperation(value = "save", notes = "save")
    @GetMapping(value="/save")
    @PreAuthorize("hasAuthority('save')")
    public HttpResult save() {
        return HttpResult.ok("the delete service is called success.");
    }

}
