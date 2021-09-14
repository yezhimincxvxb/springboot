package com.yzm.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzm.security.entity.User;

/**
 * 用户管理
 */
public interface UserService extends IService<User> {

    User findByUsername(String username);

}
