package com.yzm.security.config.cus;

import com.yzm.security.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    public static final Map<String, String> TOKEN_MAP = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("access_token");
        String refreshToken = request.getHeader("refresh_token");
        if (StringUtils.isBlank(accessToken) && StringUtils.isBlank(refreshToken)) {
            log.info("缺少 token 令牌");
            response.getWriter().write("lost token");
            return false;
        }

        if (StringUtils.isNotBlank(accessToken)) {
            Claims claims = JwtTokenUtils.verifyToken(accessToken);
            if (claims == null) {
                if (StringUtils.isBlank(refreshToken)) {
                    response.getWriter().write("old accessToken expired ! Please replace it with a new one in time");
                }
                log.info("access_token 令牌过期，尝试刷新");
            } else {
                String username = claims.getSubject();
                request.setAttribute("username", username);
                log.info("accessToken 剩余有效时间：" + (claims.getExpiration().getTime() - System.currentTimeMillis()) / 1000 + " 秒");
                return true;
            }
        }

        if (StringUtils.isNotBlank(refreshToken)) {
            Claims claims = JwtTokenUtils.verifyToken(refreshToken);
            if (claims == null) {
                response.getWriter().write("token expired, please login again!");
                log.info("refresh_token 令牌过期，请重新登录");
            } else {
                String username = claims.getSubject();
                String autoTokenRe = TOKEN_MAP.get(username);
                if (refreshToken.equalsIgnoreCase(autoTokenRe)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put(JwtTokenUtils.USERNAME, username);
                    map.put(JwtTokenUtils.PASSWORD, claims.get(JwtTokenUtils.PASSWORD));

                    map.put("type", "access");
                    String newAccessToken = JwtTokenUtils.generateToken(map, 120 * 1000L);
                    map.put("type", "refresh");
                    String newRefreshToken = JwtTokenUtils.generateToken(map, 300L * 1000L);

                    response.setHeader("access_token", newAccessToken);
                    response.setHeader("refresh_token", newRefreshToken);
                }
                request.setAttribute("username", username);
                log.info("refreshToken 剩余有效时间：" + (claims.getExpiration().getTime() - System.currentTimeMillis()) / 1000 + " 秒");
                return true;
            }
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("执行了User拦截器的 postHandle方法");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("执行了User拦截器的 afterCompletion方法");
    }
}
