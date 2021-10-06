package com.yzm.shiro.config.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Set;

public class AnyRolesAuthFilter extends AuthorizationFilter {

    public AnyRolesAuthFilter() {
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = this.getSubject(request, response);
        String[] rolesArray = (String[])mappedValue;
        if (rolesArray != null && rolesArray.length != 0) {
            //若当前用户是rolesArray中的任何一个，则有权限访问
            for (String role : rolesArray) {
                if (subject.hasRole(role)) return true;
            }
        } else {
            //没有角色限制，有权限访问
            return true;
        }
        return false;
    }

    /**
     * 权限校验失败，错误处理
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        // HttpUtils.errorWrite((HttpServletResponse) response, 401, "无权访问");
        // return false;
        return super.onAccessDenied(request, response);
    }

}