package com.yzm.security.service.impl;

import com.yzm.security.entity.Role;
import com.yzm.security.mapper.RoleMapper;
import com.yzm.security.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Yzm
 * @since 2021-09-13
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Role> getRoles(Integer userid) {
        return this.baseMapper.getRoles(userid);
    }

}
