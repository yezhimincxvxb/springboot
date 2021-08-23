package com.yzm.security.config.token;

import com.yzm.security.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

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
            Claims claims = JwtUtils.verifyToken(accessToken);
            if (claims == null) {
                response.getWriter().write("old accessToken expired ! Please replace it with a new one in time");
                log.info("token 令牌过期，尝试刷新");
            } else {
                Integer userId = claims.get("userid", Integer.class);
                request.setAttribute("userid", userId);
                log.info("accessToken 剩余有效时间：" + (claims.getExpiration().getTime() - System.currentTimeMillis() / 1000) + " 秒");
                return true;
            }
        }

        if (StringUtils.isNotBlank(refreshToken)) {
            Claims claims = JwtUtils.verifyToken(refreshToken);
            if (claims == null) {
                response.getWriter().write("token expired, please login again!");
                log.info("刷新 token 令牌过期，请重新登录");
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("userid", claims.get("userid"));
                map.put("username", claims.get("username"));
                map.put("password", claims.get("password"));
                String newAccessToken = JwtUtils.generateToken(map, 120 * 1000L);
                String newRefreshToken = JwtUtils.generateToken(map, 300 * 1000L);
                response.setHeader("access_token", newAccessToken);
                response.setHeader("refresh_token", newRefreshToken);

                Integer userId = claims.get("userid", Integer.class);
                request.setAttribute("userid", userId);

                log.info("refreshToken 剩余有效时间：" + (claims.getExpiration().getTime() - System.currentTimeMillis() / 1000) + " 秒");
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
