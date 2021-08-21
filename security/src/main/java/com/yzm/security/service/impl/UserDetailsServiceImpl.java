package com.yzm.security.service.impl;

import com.yzm.security.entity.JwtGrantedAuthority;
import com.yzm.security.entity.User;
import com.yzm.security.entity.JwtUserDetails;
import com.yzm.security.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户登录认证信息查询
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(@Qualifier("userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }

        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('delete')") 标注的接口对比，决定是否可以调用接口
        List<JwtGrantedAuthority> grantedAuthorities = Arrays.stream(user.getPermissions().split(","))
                .map(JwtGrantedAuthority::new)
                .collect(Collectors.toList());
        return new JwtUserDetails(username, user.getPassword(), grantedAuthorities);
    }
}