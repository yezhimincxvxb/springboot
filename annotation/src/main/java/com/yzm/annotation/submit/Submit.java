package com.yzm.annotation.submit;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Submit {

    /**
     * key前缀
     */
    String prefix() default "submit:";

    /**
     * 延迟时间，单位：秒
     */
    int delaySeconds() default 2;
}
