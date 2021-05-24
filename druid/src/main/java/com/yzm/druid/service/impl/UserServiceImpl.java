package com.yzm.druid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.druid.entity.User;
import com.yzm.druid.mapper.UserMapper;
import com.yzm.druid.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Yzm
 * @since 2021-04-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
