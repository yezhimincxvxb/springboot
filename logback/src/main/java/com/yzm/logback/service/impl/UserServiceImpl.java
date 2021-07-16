package com.yzm.logback.service.impl;

import com.yzm.logback.entity.User;
import com.yzm.logback.mapper.UserMapper;
import com.yzm.logback.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Yzm
 * @since 2021-07-16
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<User> listUser() {
        log.trace("trace 级别打印");
        log.debug("debug 级别打印");
        log.info("info 级别打印");
        log.warn("warn 级别打印");
        log.error("error 级别打印");
        return baseMapper.listUser();
    }


}
