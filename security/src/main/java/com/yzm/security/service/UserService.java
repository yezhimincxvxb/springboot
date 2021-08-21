package com.yzm.security.service;

import com.yzm.security.entity.User;

import java.util.Set;

/**
 * 用户管理
 */
public interface UserService {

    /**
     * 根据用户名查找用户
     */
    User findByUsername(String username);

    /**
     * 查找用户的菜单权限标识集合
     */
    Set<String> findPermissions(String username);

}
