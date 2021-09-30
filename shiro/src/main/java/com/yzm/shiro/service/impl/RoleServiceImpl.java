package com.yzm.shiro.service.impl;

import com.yzm.shiro.entity.Role;
import com.yzm.shiro.mapper.RoleMapper;
import com.yzm.shiro.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Yzm
 * @since 2021-09-03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Role> getRoles(List<Integer> roleIds) {
        return this.baseMapper.getRoles(roleIds);
    }

}
