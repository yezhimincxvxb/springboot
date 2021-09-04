package com.yzm.shiro.service.impl;

import com.yzm.shiro.entity.Role;
import com.yzm.shiro.entity.User;
import com.yzm.shiro.mapper.UserMapper;
import com.yzm.shiro.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Yzm
 * @since 2021-09-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User findUserByName(String username) {
        return this.baseMapper.findUserByName(username);
    }


}
