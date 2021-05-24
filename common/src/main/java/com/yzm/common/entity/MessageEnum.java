package com.yzm.common.entity;

import lombok.Getter;

@Getter
public enum MessageEnum {

    Error("0", "操作失败"),
    SUCCESS("200", "操作成功"),
    NOT_FOUNT("404", "请求路径错误"),
    BAD_PARAM("400", "请求参数错误");

    private String code;
    private String message;

    MessageEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
