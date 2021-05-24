package com.yzm.swagger.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserVo {
    @JSONField(ordinal = 1)
    private Integer userId;

    @JSONField(ordinal = 2)
    private String userName;

    @JSONField(ordinal = 3)
    private String nickName;

    @JSONField(ordinal = 4)
    private String password;

    @JSONField(ordinal = 5)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JSONField(ordinal = 6)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
