package com.yzm.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class IndexController {

//    private final SessionRegistry sessionRegistry;
//
//    public IndexController(SessionRegistry sessionRegistry) {
//        this.sessionRegistry = sessionRegistry;
//    }

    @RequestMapping("/sms/code")
    public void sms(String mobile, HttpSession session) {
        int code = (int) Math.ceil(Math.random() * 9000 + 1000);

        Map<String, Object> map = new HashMap<>(16);
        map.put("mobile", mobile);
        map.put("code", code);

        session.setAttribute("smsCode", map);
        log.info("{}：为 {} 设置短信验证码：{}", session.getId(), mobile, code);
    }


    /**
     * 查看登录失败具体原因
     */
    @RequestMapping("/login/error")
    public void loginError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        AuthenticationException exception =
                (AuthenticationException) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        try {
            response.getWriter().write(exception.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    @GetMapping("/kick")
//    public String removeUserSessionByUsername(@RequestParam String username) {
//        int count = 0;
//
//        // 获取session中所有的用户信息
//        List<Object> users = sessionRegistry.getAllPrincipals(); // 获取所有 principal 信息
//        for (Object principal : users) {
//            if (principal instanceof UserDetails) {
//                String principalName = ((UserDetails) principal).getUsername();
//                if (principalName.equals(username)) {
//                    // 参数二：是否包含过期的Session
//                    List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
//                    if (null != sessionsInfo && sessionsInfo.size() > 0) {
//                        for (SessionInformation sessionInformation : sessionsInfo) {
//                            // 使 session 过期
//                            sessionInformation.expireNow();
//                            count++;
//                        }
//                    }
//                }
//            }
//        }
//        return "操作成功，清理session共" + count + "个";
//    }

}
