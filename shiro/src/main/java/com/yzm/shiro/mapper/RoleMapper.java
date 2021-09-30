package com.yzm.shiro.mapper;

import com.yzm.shiro.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

    List<Role> getRoles(@Param("roleIds") List<Integer> roleIds);

}
