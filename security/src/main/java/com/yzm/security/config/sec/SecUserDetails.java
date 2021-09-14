package com.yzm.security.config.sec;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

/**
 * 安全用户模型
 */
public class SecUserDetails extends User {

    private static final long serialVersionUID = 3033317408164827323L;
    private Set<String> permissions;

    public SecUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
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

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
