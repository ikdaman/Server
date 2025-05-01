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
    // TODO: apptoken 헤더로 분리
    private String appToken;
    private String nickname;

    @Builder
    public AuthRes(String appToken, String nickname) {
        this.appToken = appToken;
        this.nickname = nickname;
    }
}
