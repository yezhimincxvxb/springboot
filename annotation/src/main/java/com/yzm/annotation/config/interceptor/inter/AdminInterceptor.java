package com.yzm.annotation.config.interceptor.inter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private static final String encoding = "UTF-8";

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     * 在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            request.setCharacterEncoding(encoding);
            response.setCharacterEncoding(encoding);
            if (request.getSession().getAttribute("USER") != null) {
                return true;
            }
            // response.sendRedirect(request.getContextPath()+"你的登陆页地址");
            response.getWriter().write("no-login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
        return false;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * 在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），
     * 有机会修改ModelAndView （这个博主就基本不怎么用了）；
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("执行了Admin拦截器的 postHandle方法");
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     * 在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面）；
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("执行了Admin拦截器的afterCompletion方法");
    }

}
