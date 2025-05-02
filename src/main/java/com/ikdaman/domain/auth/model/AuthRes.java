package com.ikdaman.domain.auth.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 소셜 로그인 응답 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthRes {
    private String accessToekn;    // App의 AccessToken
    private String refreshToken;    // RefreshToken
    private String nickname;

    @Builder
    public AuthRes(String accessToekn, String refreshToken, String nickname) {
        this.accessToekn = accessToekn;
        this.refreshToken = refreshToken;
        this.nickname = nickname;
    }
}
