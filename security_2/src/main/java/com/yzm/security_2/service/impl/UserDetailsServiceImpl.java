package com.yzm.security_2.service.impl;

import com.yzm.security_2.constant.SysConstant;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义的认证用户获取服务类
 * Spring Security进行用户认证时，需要根据用户的账号、密码、权限等信息进行认证，
 * 因此，需要根据查询到的用户信息封装成一个认证用户对象并交给Spring Security进行认证。
 * 查询用户信息并封装成认证用户对象的过程是在UserDetailsService接口的实现类（需要用户自己实现）中完成的
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = new BCryptPasswordEncoder().encode("123456");
        List<String> permissions = new ArrayList<>();
        permissions.add(SysConstant.USER);
        if (SysConstant.SUPER_ADMIN.equalsIgnoreCase(username)) {
            permissions.add(SysConstant.SUPER_ADMIN);
            permissions.add(SysConstant.ADMIN);
        } else if (SysConstant.ADMIN.equalsIgnoreCase(username)) {
            permissions.add(SysConstant.ADMIN);
        }

        List<SimpleGrantedAuthority> grantedAuthorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new User(username, password, grantedAuthorities);
    }
}