package com.yzm.security.mapper;

import com.yzm.security.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author Yzm
 * @since 2021-09-13
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRoles(Integer userid);

}
