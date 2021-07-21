package com.yzm.validation.annotation.validator;

import com.yzm.validation.annotation.IsPhone;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 手机格式校验规则
 */
public class IsPhoneValidator implements ConstraintValidator<IsPhone, String> {

    // 定义属性来接收注解类的属性下限
    private String message;

    // 获取自定义注解IsPhone的属性消息
    @Override
    public void initialize(IsPhone constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }


    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.hasLength(phone)) {
            throw new RuntimeException("手机号不为空");
        }
        boolean matches = Pattern.matches("^1([3589][0-9]|4[579]|66|7[01235678])[0-9]{8}$", phone);
        if (!matches) {
            throw new RuntimeException(message);
        }
        return true;
    }

}
