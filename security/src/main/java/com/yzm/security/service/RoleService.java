package com.yzm.security.service;

import com.yzm.security.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author Yzm
 * @since 2021-09-13
 */
public interface RoleService extends IService<Role> {

    List<Role> getRoles(Integer userid);

}
