package com.yzm.validation.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp;
}
