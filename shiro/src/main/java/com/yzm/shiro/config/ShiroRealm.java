package com.yzm.shiro.config;

import com.yzm.shiro.entity.Permissions;
import com.yzm.shiro.entity.Role;
import com.yzm.shiro.entity.User;
import com.yzm.shiro.service.PermissionsService;
import com.yzm.shiro.service.RoleService;
import com.yzm.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionsService permissionsService;

    /**
     * 角色授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findUserByName(username);
        List<Role> roles = roleService.getRoles(user.getId());
        List<Integer> roleIds = roles.stream().map(Role::getRId).collect(Collectors.toList());
        List<String> roleNames = roles.stream().map(Role::getRName).collect(Collectors.toList());
        List<Permissions> permissions = permissionsService.getPermissions(roleIds);
        List<String> permNames = permissions.stream().map(Permissions::getPName).collect(Collectors.toList());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(roleNames);
        authorizationInfo.addStringPermissions(permNames);
        return authorizationInfo;
    }

    /**
     * 登陆认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        //String username = (String) authenticationToken.getPrincipal();
        //String password = new String((char[]) authenticationToken.getCredentials());
        User user = userService.findUserByName(username);
        if (user == null) {
            return null;
        }

        //存到session中
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("user", user);
        return new SimpleAuthenticationInfo(
                user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), getName()
        );
    }
}
