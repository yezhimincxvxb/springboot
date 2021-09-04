package com.yzm.shiro.mapper;

import com.yzm.shiro.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author Yzm
 * @since 2021-09-03
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRoles(Integer userid);

}
