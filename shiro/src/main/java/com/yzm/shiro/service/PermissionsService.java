package com.yzm.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzm.shiro.entity.Permissions;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author Yzm
 * @since 2021-09-03
 */
public interface PermissionsService extends IService<Permissions> {

    List<Permissions> getPermissions(List<Integer> roleIds);

}
