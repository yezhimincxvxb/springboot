package com.yzm.annotation.config.listener;

import com.yzm.annotation.config.listener.spring.MyPublisher;
import com.yzm.annotation.config.listener.spring.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/listener")
public class ListenerDemo {

    @GetMapping("/context")
    public String context(HttpServletRequest request) {
        try {
            ServletContext context = request.getServletContext();
            context.setAttribute("context_name", "context_value");
            Thread.sleep(5000);
            context.setAttribute("context_name", "context_value_again");
            Thread.sleep(5000);
            context.removeAttribute("context_name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "context 属性变化";
    }

    @GetMapping("/session")
    public String session(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            session.setAttribute("session_name", "session_value");
            Thread.sleep(5000);
            session.setAttribute("session_name", "session_value_again");
            Thread.sleep(5000);
            session.removeAttribute("session_name");
            session.setMaxInactiveInterval(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "session 属性变化";
    }

    @GetMapping("/request")
    public String request(HttpServletRequest request) {
        try {
            request.setAttribute("request_name", "request_value");
            Thread.sleep(5000);
            request.setAttribute("request_name", "request_value_again");
            Thread.sleep(5000);
            request.removeAttribute("request_name");
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "request 属性变化";
    }

    @Autowired
    private MyPublisher myPublisher;

    @GetMapping("/publisher")
    public void publisher() {
        myPublisher.publish(new Task("张三", "木匠"));
    }

}
