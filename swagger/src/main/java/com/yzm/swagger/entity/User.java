package com.yzm.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("用户对象")
public class User {
    @ApiModelProperty(name = "userId", value = "用户ID", example = "1")
    private Integer userId;
    @ApiModelProperty(name = "userName", value = "用户姓名", example = "aaa")
    private String userName;
    @ApiModelProperty(name = "nickName", value = "用户昵称", example = "AAA")
    private String nickName;
    @ApiModelProperty(name = "password", value = "登录密码", example = "123")
    private String password;
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private Date createTime;
    @ApiModelProperty(name = "updateTime", value = "更新时间")
    private Date updateTime;
}
