package com.yzm.shiro.config;

import com.yzm.shiro.entity.JwtToken;
import com.yzm.shiro.entity.User;
import com.yzm.shiro.service.UserService;
import com.yzm.shiro.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class JwtShiroRealm extends AuthorizingRealm {

    protected UserService userService;

    public JwtShiroRealm(UserService userService) {
        this.userService = userService;
    }

    /**
     * 限定这个Realm只支持我们自定义的JWT Token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 更controller登录一样，也是获取用户的salt值，给到shiro，由shiro来调用matcher来做认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authcToken;
        String token = jwtToken.getToken();

        String username = JwtUtils.getUsernameFromToken(token);
        if (username == null) {
            throw new AuthenticationException("token过期，请重新登录");
        }

        User user = userService.findUserByName(username);
        if (user == null) {
            throw new AuthenticationException("账号异常");
        }

        return new SimpleAuthenticationInfo(
                user.getUsername(), token, ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) { return null; }

}
