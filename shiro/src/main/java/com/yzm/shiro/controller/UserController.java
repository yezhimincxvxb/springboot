package com.yzm.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @RequiresAuthentication
    @GetMapping("index")
    public Object index() {
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession().getAttribute("user");
    }

    @GetMapping("admin")
    public Object admin() {
        return "Welcome Admin";
    }

    @GetMapping("user")
    public Object user() {
        return "Welcome User";
    }

    @GetMapping("edit")
    public Object edit() {
        return "edit";
    }

    @GetMapping("remove")
    public Object remove() {
        return "remove";
    }

    @RequiresPermissions("select")
    @GetMapping("select")
    public Object select() {
        return "Select";
    }

    @RequiresPermissions(value = {"create", "update"}, logical = Logical.AND)
    @GetMapping("create")
    public Object create() {
        return "Create";
    }

    @RequiresPermissions(value = {"update", "delete"}, logical = Logical.OR)
    @GetMapping("update")
    public Object update() {
        return "Update";
    }

    @RequiresPermissions("delete")
    @GetMapping("delete")
    public Object delete() {
        return "Delete";
    }

}
