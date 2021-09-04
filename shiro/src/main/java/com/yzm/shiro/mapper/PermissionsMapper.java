package com.yzm.shiro.mapper;

import com.yzm.shiro.entity.Permissions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author Yzm
 * @since 2021-09-03
 */
public interface PermissionsMapper extends BaseMapper<Permissions> {

    List<Permissions> getPermissions(@Param("roleIds") List<Integer> roleIds);

}
