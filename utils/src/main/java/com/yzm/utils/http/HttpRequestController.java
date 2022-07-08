package com.yzm.utils.http;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/http")
public class HttpRequestController {

    @GetMapping(path = "/test01")
    public void test01() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        // 获取请求参数(方式一)
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String element = parameterNames.nextElement();
            System.out.println(element + "：" + Arrays.toString(request.getParameterValues(element)));
        }

        // 获取请求参数(方式二)
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((key, value) -> System.out.println(key + "：" + Arrays.toString(value)));

        System.out.println("Method = " + request.getMethod());

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String element = headerNames.nextElement();
            System.out.println(element + "=" + request.getHeader(element));
        }
    }

}
