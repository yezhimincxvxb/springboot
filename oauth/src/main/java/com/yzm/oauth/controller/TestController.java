package com.yzm.oauth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {

    @GetMapping("/product/{id}")
    @ResponseBody
    public String getProduct(@PathVariable String id) {
        // 用于debug查看
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "product id : " + id;
    }

    @GetMapping("/order/{id}")
    @ResponseBody
    public String getOrder(@PathVariable String id) {
        return "order id : " + id;
    }

}
