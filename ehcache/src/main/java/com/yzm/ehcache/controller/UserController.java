package com.yzm.ehcache.controller;


import com.yzm.ehcache.entity.User;
import com.yzm.ehcache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Yzm
 * @since 2021-02-24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public boolean saveUser(@RequestBody User user) {
        return userService.saveUser(user) != null;
    }

    @PostMapping("/updateUser")
    public int updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/deleteUser")
    public int deleteUser(@RequestParam("id") Integer id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/findById")
    public User findById(@RequestParam("id") Integer id) {
        return userService.findById(id);
    }

    @GetMapping("/selectById")
    public User selectById(@RequestParam("id") Integer id) {
        return userService.selectById(id);
    }

    @PostMapping("/getById")
    public User getById(@RequestBody User user) {
        return userService.getById(user);
    }

    @GetMapping("/listAll")
    public List<User> listAll() {
        return userService.listAll();
    }

    @GetMapping("/deleteAll")
    public void deleteAll() {
        userService.deleteAll();
    }

    @GetMapping("/selectCache")
    public void selectCache(@RequestParam("id") Integer id) {
        userService.selectCache(id);
    }

}
