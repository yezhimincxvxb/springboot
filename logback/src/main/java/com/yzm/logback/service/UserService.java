package com.yzm.logback.service;

import com.yzm.logback.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author Yzm
 * @since 2021-07-16
 */
public interface UserService extends IService<User> {

    List<User> listUser();

}
