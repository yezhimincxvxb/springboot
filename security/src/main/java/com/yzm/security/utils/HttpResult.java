package com.yzm.security.utils;


import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * HTTP结果封装
 */
@Data
public class HttpResult implements Serializable {

    private static final long serialVersionUID = -4661003546224340096L;

    private int code;
    private String msg;
    private Object data;

    public HttpResult(int code, String msg) {
        this(code, msg, null);
    }

    public HttpResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static HttpResult error() {
        return new HttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
    }

    public static HttpResult error(String msg) {
        return new HttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static HttpResult error(int code, String msg) {
        return new HttpResult(code, msg);
    }

    public static HttpResult ok() {
        return new HttpResult(HttpStatus.OK.value(), "操作成功");
    }

    public static HttpResult ok(String msg) {
        return new HttpResult(HttpStatus.OK.value(), msg);
    }

    public static HttpResult ok(Object data) {
        return new HttpResult(HttpStatus.OK.value(), "操作成功", data);
    }

}