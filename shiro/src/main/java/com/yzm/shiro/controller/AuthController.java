package com.yzm.shiro.controller;

import com.yzm.shiro.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @GetMapping("index")
    public Object index() {
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession().getAttribute("user");
    }

    @GetMapping("admin")
    public Object admin() {
        return "Welcome Admin";
    }

    @GetMapping("edit")
    public Object edit() {
        return "edit";
    }

    @GetMapping("remove")
    public Object remove() {
        return "remove";
    }

    @RequiresRoles("SUPER_ADMIN")
    @GetMapping("supAdmin")
    public Object supAdmin() {
        return "Welcome supAdmin";
    }

    @RequiresPermissions("Create")
    @GetMapping("create")
    public Object create() {
        return "Create";
    }

    @RequiresPermissions("Update")
    @GetMapping("update")
    public Object update() {
        return "Update";
    }

    @RequiresPermissions("Delete")
    @GetMapping("delete")
    public Object delete() {
        return "Delete";
    }

    @RequiresPermissions("Select")
    @GetMapping("select")
    public Object select() {
        return "Select";
    }

}
