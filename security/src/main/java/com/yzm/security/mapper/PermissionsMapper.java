package com.yzm.security.mapper;

import com.yzm.security.entity.Permissions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Yzm
 * @since 2021-08-21
 */
public interface PermissionsMapper extends BaseMapper<Permissions> {

    List<Permissions> getPermissions(@Param("roleIds") List<Integer> roleIds);

}
