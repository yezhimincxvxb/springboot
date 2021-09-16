package com.yzm.security.config;

import com.yzm.security.entity.Role;
import com.yzm.security.entity.User;
import com.yzm.security.service.RoleService;
import com.yzm.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public SecUserDetailsServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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

            List<Role> roleList = roleService.getRoles(user.getId());
            List<Integer> roleIds = roleList.stream().map(Role::getRId).collect(Collectors.toList());
            List<SimpleGrantedAuthority> authorities = roleList.stream()
                    .map(Role::getRName)
                    .distinct()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            return new SecUserDetails(username, user.getPassword(), authorities, roleIds);
        }
    }
}