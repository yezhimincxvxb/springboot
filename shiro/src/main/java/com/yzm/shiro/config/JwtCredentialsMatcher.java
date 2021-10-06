package com.yzm.shiro.config;

import com.yzm.shiro.entity.JwtToken;
import com.yzm.shiro.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

public class JwtCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        if (authenticationToken instanceof JwtToken) {
            String token = (String) authenticationInfo.getCredentials();
            return JwtUtils.verifyToken(token) != null;
        }
        return false;
    }

}
