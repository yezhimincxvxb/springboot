package com.yzm.shiro.entity;

import org.apache.shiro.authc.HostAuthenticationToken;

public class JwtToken implements HostAuthenticationToken {
    private static final long serialVersionUID = -4763390136373610135L;
    private String token;
    private String host;

    public JwtToken(String token) {
        this(token, null);
    }

    public JwtToken(String token, String host) {
        this.token = token;
        this.host = host;
    }

    public String getToken(){
        return this.token;
    }
    public String getHost() {
        return host;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }
    @Override
    public Object getCredentials() {
        return token;
    }
    @Override
    public String toString(){
        return token + ':' + host;
    }
}