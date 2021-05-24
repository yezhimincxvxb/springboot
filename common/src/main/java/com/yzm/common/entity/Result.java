package com.yzm.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 7374073564792172654L;

    // 状态码
    @ApiModelProperty(value = "状态码", position = 1)
    private String code;
    // 提示信息
    @ApiModelProperty(value = "提示信息", position = 2)
    private String msg;
    // 返回数据
    @ApiModelProperty(value = "返回数据", position = 3)
    private T data;

    public Result() {
        this(null);
    }

    public Result(T data) {
        this(MessageEnum.SUCCESS, data);
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(MessageEnum messageEnum, T data) {
        this.code = messageEnum.getCode();
        this.msg = messageEnum.getMessage();
        this.data = data;
    }
}
