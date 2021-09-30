package com.yzm.security.config;

import com.yzm.security.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码过滤器
 * 拦截/login登录请求。检验验证码
 */
@Slf4j
public class VerifyFilter extends OncePerRequestFilter {

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //拦截 /login的POST请求
        if ("POST".equals(request.getMethod()) && pathMatcher.match("/login", request.getRequestURI())) {
            log.info(request.getRequestURI());
            //获取用户输入的验证码
            String inputVerify = request.getParameter("verifyCode");
            //这个validateCode是在servlet中存入session的名字
            String validateCode = (String) request.getSession().getAttribute("validateCode");
            log.info("验证码：" + validateCode);
            log.info("用户输入：" + inputVerify);
            if (!validateCode.equalsIgnoreCase(inputVerify)) {
                //验证失败 手动设置异常
                request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", new DisabledException("验证码输入错误"));
                // 转发到错误Url
                request.getRequestDispatcher("/login/error").forward(request, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
