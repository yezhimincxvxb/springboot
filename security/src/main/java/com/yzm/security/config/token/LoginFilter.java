package com.yzm.security.config.token;

import com.yzm.security.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.FilterConfig;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class LoginFilter implements Filter {

    // 配置白名单
    private final List<Pattern> patterns = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化：" + filterConfig.getFilterName());
        String excludedUris = filterConfig.getInitParameter("excludedUris");
        if (StringUtils.isNotBlank(excludedUris)) {
            String[] splits = excludedUris.split(",");
            for (String split : splits) {
                this.patterns.add(Pattern.compile(split));
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isInclude(request.getRequestURI())) {
            log.info("filter 《==》 白名单，放行访问");
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("auth_token");
        if (StringUtils.isBlank(token)) {
            log.info("缺少 token 令牌");
            response.getWriter().write("lost token");
            return;
        }

        Claims claims = JwtUtils.verifyToken(token);
        if (claims == null) {
            response.getWriter().write("token expired，please login again!");
            return;
        }

        Date iat = claims.getIssuedAt();
        long currentTime = System.currentTimeMillis();
        long refreshTime = iat.getTime() + JwtUtils.TOKEN_REFRESH_TIME;
        long expireTime = claims.getExpiration().getTime();

        //大于刷新时间且在有效期内，进行刷新token
        if (currentTime > refreshTime && currentTime < expireTime) {
            Map<String, Object> map = new HashMap<>();
            map.put("userid", claims.get("userid"));
            map.put("username", claims.get("username"));
            map.put("password", claims.get("password"));
            String autoTokenRe = JwtUtils.generateToken(map);
            response.setHeader("auth_token", autoTokenRe);
            log.info("token 令牌 刷新");
        } else {
            log.info("token 有效期：" + (claims.getExpiration().getTime() - System.currentTimeMillis()) / 1000 + " 秒");
        }

        Integer userId = claims.get("userid", Integer.class);
        request.setAttribute("userid", userId);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }

    //判断当前请求是否在白名单
    private boolean isInclude(String url) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }
}
