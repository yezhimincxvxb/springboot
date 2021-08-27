package com.yzm.security.service.impl;

import com.yzm.security.entity.User;
import com.yzm.security.jwt.JwtUserDetails;
import com.yzm.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
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

            // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('delete')") 标注的接口对比，决定是否可以调用接口
            Set<String> permissions = userService.findPermissions(username);
            List<SimpleGrantedAuthority> grantedAuthorities = permissions.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            return new JwtUserDetails(username, user.getPassword(), grantedAuthorities);
        }
    }
}