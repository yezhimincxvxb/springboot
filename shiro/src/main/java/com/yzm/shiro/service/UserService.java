package com.yzm.shiro.service;

import com.yzm.shiro.entity.Role;
import com.yzm.shiro.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Yzm
 * @since 2021-09-03
 */
public interface UserService extends IService<User> {

    User findUserByName(String username);

}
