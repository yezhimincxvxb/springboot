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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleShiroRealm extends AuthorizingRealm {

    private final UserService userService;
    private final RoleService roleService;
    private final PermissionsService permissionsService;

    public SimpleShiroRealm(UserService userService, RoleService roleService, PermissionsService permissionsService) {
        this.userService = userService;
        this.roleService = roleService;
        this.permissionsService = permissionsService;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

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
        List<Integer> roleIds = Arrays.stream(user.getRIds().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Role> roles = roleService.getRoles(roleIds);
        Set<String> roleNames = new HashSet<>(roles.size());
        Set<Integer> permIds = new HashSet<>();
        roles.forEach(role -> {
            roleNames.add(role.getRName());
            Set<Integer> collect = Arrays.stream(
                    role.getPIds().split(",")).map(Integer::parseInt).collect(Collectors.toSet());
            permIds.addAll(collect);
        });

        List<Permissions> permissions = permissionsService.getPermissions(permIds);
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
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        //String username = (String) authenticationToken.getPrincipal();
        //String password = new String((char[]) authenticationToken.getCredentials());
        User user = userService.findUserByName(username);
        if (user == null) {
            throw new UnknownAccountException();
        }

        //存到session中
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("user", user);
        return new SimpleAuthenticationInfo(
                user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), getName()
        );
    }
}