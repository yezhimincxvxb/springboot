package com.yzm.security.config.sec;

import com.yzm.security.entity.Permissions;
import com.yzm.security.entity.Role;
import com.yzm.security.entity.User;
import com.yzm.security.service.PermissionsService;
import com.yzm.security.service.RoleService;
import com.yzm.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义的认证用户获取服务类
 * Spring Security进行用户认证时，需要根据用户的账号、密码、权限等信息进行认证，
 * 因此，需要根据查询到的用户信息封装成一个认证用户对象并交给Spring Security进行认证。
 * 查询用户信息并封装成认证用户对象的过程是在UserDetailsService接口的实现类（需要用户自己实现）中完成的
 */
@Service
public class SecUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final RoleService roleService;
    private final PermissionsService permissionsService;

    public SecUserDetailsServiceImpl(UserService userService, RoleService roleService, PermissionsService permissionsService) {
        this.userService = userService;
        this.roleService = roleService;
        this.permissionsService = permissionsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户账号为空");
        } else {
            User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException(String.format("用户'%s'不存在", username));
            }

            List<Role> roles = roleService.getRoles(user.getId());
            List<Integer> roleIds = roles.stream().map(Role::getRId).collect(Collectors.toList());

            // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('delete')") 标注的接口对比，决定是否可以调用接口
            List<Permissions> permissionsList = permissionsService.getPermissions(roleIds);
            Set<String> permissions = permissionsList.stream().map(Permissions::getPName).collect(Collectors.toSet());
            List<SimpleGrantedAuthority> grantedAuthorities = permissions.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            return new SecUserDetails(username, user.getPassword(), grantedAuthorities);
        }
    }
}