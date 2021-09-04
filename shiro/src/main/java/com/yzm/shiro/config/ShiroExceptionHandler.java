package com.yzm.shiro.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 使用注解：@RequiresPermissions、@RequiresRoles设置角色或者权限
 * 是不会被shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");正确的返回给前端错误信息的
 * 所以自定义异常拦截类
 */
@Slf4j
@ControllerAdvice
public class ShiroExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public String ErrorHandler(AuthorizationException e) {
        log.error("没有通过权限验证！");
        return "没有通过权限验证！";
    }
}
