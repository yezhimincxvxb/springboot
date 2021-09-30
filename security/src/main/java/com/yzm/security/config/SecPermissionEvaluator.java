package com.yzm.security.config;

import com.yzm.security.config.SecUserDetails;
import com.yzm.security.entity.Permissions;
import com.yzm.security.service.PermissionsService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 细粒度的权限控制
 */
@Component
public class SecPermissionEvaluator implements PermissionEvaluator {

    private final PermissionsService permissionsService;

    public SecPermissionEvaluator(PermissionsService permissionsService) {
        this.permissionsService = permissionsService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {
        // 获得loadUserByUsername()方法的结果
        SecUserDetails userDetails = (SecUserDetails) authentication.getPrincipal();
        // 获得loadUserByUsername()中注入的角色
        //Collection<GrantedAuthority> authorities = userDetails.getAuthorities();

        // 查询角色对应的所有权限信息
        List<Integer> roleIds = userDetails.getRoleIds();
        List<Permissions> perms = permissionsService.getPerms(roleIds);

        for (Permissions perm : perms) {
            // 如果访问的Url和权限用户符合的话，返回true
            if (targetUrl.equals(perm.getUrl()) && perm.getPerms().contains((String) targetPermission)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

}
