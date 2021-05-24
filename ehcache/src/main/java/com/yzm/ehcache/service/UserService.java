package com.yzm.ehcache.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzm.ehcache.entity.User;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author Yzm
 * @since 2021-02-24
 */
public interface UserService extends IService<User> {
    User saveUser(User user);

    int updateUser(User user);

    int deleteUser(Integer id);

    User findById(Integer id);

    User selectById(Integer id);

    User getById(User user);

    List<User> listAll();

    void deleteAll();

    void selectCache(Object o);
}
