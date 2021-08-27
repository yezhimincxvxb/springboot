package com.yzm.security.controller;

import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 登录控制器
 */
@Controller
@Api(value = "jwt登录", tags = {"jwt登录API"})
public class LoginController {

    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 跳转到home.html页面（不需要登录）
     */
    @GetMapping("/user/login")
    public String authLogin() {
        return "login";
    }

    @RequestMapping("/loginFail")
    public String loginFail(){
        return "loginFail";
    }

    /**
     * 跳转到home.html页面（不需要登录）
     */
    @RequestMapping("/toHome")
    public String toHome() {
        return "home";
    }

    /**
     * 用户认证成功之后，可以通过@AuthenticationPrincipal注解来获取认证用户信息
     */
    @GetMapping("/getUserDetails")
    @ResponseBody
    public Object getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

    /**
     * 跳转到admin.html页面（需要登录，且需要ROLE_ADMIN角色）
     */
    @GetMapping("/toAdmin")
    @PreAuthorize("hasAuthority('DELETE')")
    public String toAdmin() {
        return "admin";
    }

    /**
     * 跳转到user.html页面（需要登录，但不需要角色）
     * 注意：虽然WebSecurityConfig中配置了/toUser不需要登录，但是这里配置的权限更小，因此，/toUser以这里的配置为准
     */
    @GetMapping("/toUser")
    @PreAuthorize("isAuthenticated()")
    public String toUser() {
        return "user";
    }

    /**
     * 登录接口
     */
//    @PostMapping(value = "/login")
//    public HttpResult login(@RequestBody LoginUser loginBean, HttpServletRequest request) throws IOException {
//        String username = loginBean.getUsername();
//        String password = loginBean.getPassword();
//
//        // 系统登录认证
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
//        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        // 执行登录认证过程
//        Authentication authentication = authenticationManager.authenticate(authToken);
//        // 认证成功存储认证信息到上下文
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        // 生成令牌并返回给客户端
//        return HttpResult.ok(JwtTokenUtils.generateToken(authentication));
//    }

}