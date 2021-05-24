package com.yzm.validation.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ResponseError {
    @JSONField(ordinal = 1)
    private String path;
    @JSONField(ordinal = 2)
    private String method;
    @JSONField(ordinal = 3)
    private Integer status;
    @JSONField(ordinal = 4)
    private String error;
    @JSONField(ordinal = 5)
    private String message;
    @JSONField(ordinal = 6)
    private Date timestamp;
}
