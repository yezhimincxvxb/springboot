package com.yzm.jwt.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResult {
    private String accessToken;
    private String refreshToken;
}
