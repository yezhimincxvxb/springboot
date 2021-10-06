package com.yzm.shiro.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public Object admin() {
        return "Welcome admin";
    }

    @GetMapping("edit")
    public Object edit() {
        return "edit";
    }

    @GetMapping("remove")
    public Object remove() {
        return "remove";
    }

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

    @GetMapping("delete")
    public Object delete() {
        return "Delete";
    }

}
