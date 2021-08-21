package com.yzm.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzm.security.entity.User;

import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Yzm
 * @since 2021-08-20
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查找用户
     */
    User findByUsername(String username);

    /**
     * 查找用户的菜单权限标识集合
     */
    Set<String> findPermissions(String username);

}
