package com.yzm.annotation.serialize;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * 允许在执行{@code @ResponseBody} *或{@code ResponseEntity}控制器方法之后但在使用{@code HttpMessageConverter}编写主体之前自定义响应
 */
@RestControllerAdvice
public class CustomResponseBodyAdvice implements ResponseBodyAdvice {

    /**
     * 有自定义注解 @SerializeField、@SerializeFields的方法放行
     * 该方法返回true才会执行下面的beforeBodyWrite
     *
     * @param methodParameter 控制器方法的返回类型
     * @param aClass          选择的转换器类型
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.getMethod().isAnnotationPresent(SerializeField.class) || methodParameter.getMethod().isAnnotationPresent(SerializeFields.class);
    }

    /**
     * 在选择{@code HttpMessageConverter}之后且在*的write方法被调用之前调用。
     *
     * @param o                  方法返回值对象
     * @param methodParameter    控制器方法的返回类型
     * @param mediaType          内容协商选择的内容类型
     * @param aClass             选择的转换器类型
     * @param serverHttpRequest  当前请求
     * @param serverHttpResponse 当前响应
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o == null) return null;
        // 存储运输信息对象
        JsonFilterObject jsonFilterObject = new JsonFilterObject();
        // 获取自定义注解信息
        Annotation[] ans = methodParameter.getMethodAnnotations();
        Arrays.asList(ans).forEach(an -> {
            if (an instanceof SerializeField) {
                SerializeField value = (SerializeField) an;
                jsonFilterObject.putToMap(value);
            }
            if (an instanceof SerializeFields) {
                SerializeFields values = (SerializeFields) an;
                for (SerializeField value : values.value()) {
                    jsonFilterObject.putToMap(value);
                }
            }
        });

        return jsonFilterObject.setObject(o);
    }
}
