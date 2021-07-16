package com.yzm.logback.mapper;

import com.yzm.logback.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author Yzm
 * @since 2021-07-16
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> listUser();

}
