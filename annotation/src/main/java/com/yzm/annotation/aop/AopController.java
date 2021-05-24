package com.yzm.annotation.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
public class AopController {
    @Autowired
    private AopService aopService;

    @GetMapping("/add")
    public String addUser() {
        return aopService.add();
    }

    @GetMapping("/update")
    public String updateUser() {
        return aopService.update();
    }
}
