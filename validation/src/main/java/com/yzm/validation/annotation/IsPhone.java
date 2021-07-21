package com.yzm.validation.annotation;


import com.yzm.validation.annotation.validator.IsPhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义手机格式校验注解
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsPhoneValidator.class}) // 验证器
public @interface IsPhone {

    // 约束注解验证时的输出消息
    String message() default "手机号码格式错误@";

    // 约束注解在验证时所属的组别
    Class<?>[] groups() default {};

    // 约束注解的有效负载
    Class<? extends Payload>[] payload() default {};

}

