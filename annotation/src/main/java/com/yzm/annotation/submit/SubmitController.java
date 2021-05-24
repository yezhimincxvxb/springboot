package com.yzm.annotation.submit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubmitController {

    @Submit(delaySeconds = 5)
    @GetMapping(path = "submit")
    public void submit(String a, String b, String c) {
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
}
