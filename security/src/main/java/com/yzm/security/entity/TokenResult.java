package com.yzm.security.entity;

import lombok.Data;

@Data
public class TokenResult {
    private String accessToken;
    private String refreshToken;
}
