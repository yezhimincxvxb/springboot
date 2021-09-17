package com.yzm.security.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 安全用户模型
 */
public class SecUserDetails extends User {

    private static final long serialVersionUID = 3033317408164827323L;
    //
    private List<Integer> roleIds;
    private Set<String> permissions;

    public SecUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    public SecUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, List<Integer> roleIds) {
        this(username, password, true, true, true, true, authorities);
        this.roleIds = roleIds;
    }

    public SecUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Set<String> permissions) {
        this(username, password, true, true, true, true, authorities);
        this.permissions = permissions;
    }

    public SecUserDetails(
            String username,
            String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
