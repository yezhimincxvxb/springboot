package com.yzm.validation.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Data
public class Dog {
    @NotBlank(message = "花色不为空")
    private String color;
    @Range(min = 1, max = 20, message = "年龄取值范围：1~20")
    private Integer age;
}
