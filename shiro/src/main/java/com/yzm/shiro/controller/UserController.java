package com.yzm.shiro.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiresRoles("USER")
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public Object user() {
        return "Welcome User";
    }

    @RequiresPermissions("select")
    @GetMapping("select")
    public Object select() {
        return "Select";
    }

    @RequiresPermissions(value = {"create", "update"}, logical = Logical.AND)
    @GetMapping("create")
    public Object create() {
        return "Create And Update";
    }

    @RequiresPermissions(value = {"update", "delete"}, logical = Logical.OR)
    @GetMapping("update")
    public Object update() {
        return "Update Or Delete";
    }

    @RequiresPermissions("delete")
    @GetMapping("delete")
    public Object delete() {
        return "Delete";
    }

}
