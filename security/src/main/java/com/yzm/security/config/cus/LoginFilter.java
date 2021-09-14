package com.yzm.security.config.cus;

import com.yzm.security.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.FilterConfig;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class LoginFilter implements Filter {

    // 配置白名单
    private final List<Pattern> patterns = new ArrayList<>();
    public static final Map<String, String> TOKEN_MAP = new ConcurrentHashMap<>();

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
            log.info("url：{} 《==》 白名单，放行访问", request.getRequestURI());
            chain.doFilter(request, response);
            return;
        }

        String token = JwtTokenUtils.getTokenFromRequest(request);
        if (StringUtils.isBlank(token)) {
            log.info("缺少 token 令牌");
            response.getWriter().write("lost token");
            return;
        }

        Claims claims = JwtTokenUtils.verifyToken(token);
        if (claims == null) {
            response.getWriter().write("token expired，please login again!");
            return;
        }

        Date iat = claims.getIssuedAt();
        long currentTime = System.currentTimeMillis();
        long refreshTime = iat.getTime() + JwtTokenUtils.TOKEN_REFRESH_TIME;
        long expireTime = claims.getExpiration().getTime();

        //大于刷新时间且在有效期内，进行刷新token
        String username = claims.getSubject();
        boolean b = false;
        if (currentTime > refreshTime && currentTime < expireTime) {
            b = true;
            String autoTokenRe = TOKEN_MAP.get(username);
            if (token.equalsIgnoreCase(autoTokenRe)) {
                Map<String, Object> map = new HashMap<>();
                map.put(JwtTokenUtils.USERNAME, username);
                map.put(JwtTokenUtils.PASSWORD, claims.get(JwtTokenUtils.PASSWORD));
                autoTokenRe = JwtTokenUtils.generateToken(map);
                TOKEN_MAP.put(username, autoTokenRe);
            }
            response.setHeader(JwtTokenUtils.TOKEN_HEADER, autoTokenRe);
            log.info("token 令牌 已刷新，请及时更新");
        }

        log.info("{} token 有效期：" + (claims.getExpiration().getTime() - System.currentTimeMillis()) / 1000 + " 秒", b ? "old" : "new");
        request.setAttribute("username", username);
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
