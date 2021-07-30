package com.yzm.selenium.task;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.codeborne.selenide.Selenide.open;

@Component
public class DemoTask {

    @PostConstruct
    public void test() {
        open("");


    }

}
