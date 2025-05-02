package com.ikdaman.domain.auth.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 소셜 로그인 요청 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthReq {
    private String provider; // 인증업체 NAVER, KAKAO, GOOGLE, APPLE
    private String providerId;

    @Builder
    public AuthReq(String provider, String providerId) {
        this.provider = provider;
        this.providerId = providerId;
    }
}
