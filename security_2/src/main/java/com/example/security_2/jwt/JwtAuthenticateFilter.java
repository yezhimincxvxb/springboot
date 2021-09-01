package com.example.security_2.jwt;


import com.example.security_2.utils.HttpUtils;
import com.example.security_2.utils.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证过滤器
 * <p>
 * 作用：拦截“/login”登录请求，处理表单提交的登录认证
 */
public class JwtAuthenticateFilter extends UsernamePasswordAuthenticationFilter {

    public JwtAuthenticateFilter(AuthenticationManager authManager) {
        setAuthenticationManager(authManager);
        // 可以修改默认登录地址
        //super.setFilterProcessesUrl("/auth/login");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // POST 请求 /login 登录时拦截， 由此方法触发执行登录认证流程，可以在此覆写整个登录认证逻辑
        super.doFilter(req, res, chain);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new UsernameNotFoundException("获取用户认证信息失败");
        }

        // 将请求中的认证信息包括username,password等封装成UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        this.setDetails(request, authToken);
        return this.getAuthenticationManager().authenticate(authToken);
    }

    // 成功验证后调用的方法 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        // 登录成功之后，把认证后的 Authentication 对象存储到请求线程上下文，这样在授权阶段就可以获取到此认证信息进行访问控制判断
        SecurityContextHolder.getContext().setAuthentication(authResult);

        // 记住我服务
        getRememberMeServices().loginSuccess(request, response, authResult);

        // 触发事件监听器
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }

        // 生成并返回token给客户端，后续访问携带此token
        HttpUtils.successWrite(response, JwtTokenUtils.generateToken(authResult));

        //重定向登录成功地址
        //getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    // 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        getRememberMeServices().loginFail(request, response);
        HttpUtils.errorWrite(response, "authentication failed, reason: " + failed.getMessage());
        //getFailureHandler().onAuthenticationFailure(request, response, failed);
    }
}
