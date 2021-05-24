package com.yzm.validation.entity;

import com.yzm.validation.annotation.Insert;
import com.yzm.validation.annotation.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ValidGroup {

    //Default分组
    @NotNull
    private Integer id;

    //Insert，Update
    @NotBlank(groups = {Insert.class, Update.class}, message = "username不能为空")
    private String username;

    @NotBlank(message = "密码不为空")
    private String password;

    @NotNull(groups = {Insert.class}, message = "age不能为空")
    private Integer age;

}
