package com.yzm.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "dog", description = "狗信息", parent = User.class)
public class Dog {
    @ApiModelProperty(name = "name", value = "狗名字", position = 1, example = "旺财")
    private String name;
    @ApiModelProperty(name = "age", value = "狗年龄", position = 2, example = "2")
    private String age;
}
