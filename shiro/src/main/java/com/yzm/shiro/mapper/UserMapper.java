package com.yzm.shiro.mapper;

import com.yzm.shiro.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Yzm
 * @since 2021-09-03
 */
public interface UserMapper extends BaseMapper<User> {

    User findUserByName(String username);

}
