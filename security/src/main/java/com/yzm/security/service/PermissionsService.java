package com.yzm.security.service;

import com.yzm.security.entity.Permissions;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Yzm
 * @since 2021-08-21
 */
public interface PermissionsService extends IService<Permissions> {

    List<Permissions> getPermissions(List<Integer> roleIds);

}
