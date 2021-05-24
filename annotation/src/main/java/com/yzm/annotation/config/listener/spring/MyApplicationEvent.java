package com.yzm.annotation.config.listener.spring;

import lombok.*;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class MyApplicationEvent extends ApplicationEvent {

    private Task task;

    public MyApplicationEvent(Object source) {
        super(source);
    }

    public MyApplicationEvent(Object source, Task task) {
        super(source);
        this.task = task;
    }

}

