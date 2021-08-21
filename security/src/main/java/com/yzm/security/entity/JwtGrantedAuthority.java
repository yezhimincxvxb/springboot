package com.yzm.security.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * 权限封装
 */
public class JwtGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = 7061393619549852281L;
    private String authority;

    public JwtGrantedAuthority(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}