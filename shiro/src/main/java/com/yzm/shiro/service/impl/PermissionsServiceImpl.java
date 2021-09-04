package com.yzm.shiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzm.shiro.entity.Permissions;
import com.yzm.shiro.mapper.PermissionsMapper;
import com.yzm.shiro.service.PermissionsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author Yzm
 * @since 2021-09-03
 */
@Service
public class PermissionsServiceImpl extends ServiceImpl<PermissionsMapper, Permissions> implements PermissionsService {

    @Override
    public List<Permissions> getPermissions(List<Integer> roleIds) {
        return this.baseMapper.getPermissions(roleIds);
    }
}
