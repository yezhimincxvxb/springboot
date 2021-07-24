package com.yzm.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "user", description = "用户信息")
public class User {
    @ApiModelProperty(name = "userId", value = "用户ID", position = 1, example = "1")
    private Integer userId;
    @ApiModelProperty(name = "userName", value = "用户姓名", position = 2, example = "aaa")
    private String userName;
    @ApiModelProperty(name = "password", value = "登录密码", position = 3, example = "123")
    private String password;
    @ApiModelProperty(name = "money", value = "存款", position = 4, example = "10000.00")
    private BigDecimal money;
    @ApiModelProperty(name = "createTime", value = "创建时间", position = 5)
    private Date createTime;

    @ApiModelProperty(name = "hobby", value = "兴趣爱好", position = 6)
    private String[] hobby;
    @ApiModelProperty(name = "dogs", value = "狗狗", position = 7)
    private List<Dog> dogs;
    @ApiModelProperty(name = "car", value = "车", position = 8)
    private Car car;
}
