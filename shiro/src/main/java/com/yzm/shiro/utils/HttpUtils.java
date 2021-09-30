package com.yzm.shiro.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * HTTP工具类
 */
public class HttpUtils {

    /**
     * 获取HttpServletRequest对象
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 输出信息到浏览器
     */
    public static void successWrite(HttpServletResponse response, Object data) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        HttpResult result = HttpResult.ok(data);
        response.getWriter().print(JSONObject.toJSONString(result, true));
        response.getWriter().flush();
        response.getWriter().close();
    }

    public static void errorWrite(HttpServletResponse response, String msg) throws IOException {
        errorWrite(response, 500, msg);
    }

    public static void errorWrite(HttpServletResponse response, int code, String msg) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        HttpResult result = HttpResult.error(code, msg);
        response.getWriter().print(JSONObject.toJSONString(result, true));
        response.getWriter().flush();
        response.getWriter().close();
    }

}
