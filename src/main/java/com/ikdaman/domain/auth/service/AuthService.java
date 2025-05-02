package com.ikdaman.domain.auth.service;

import com.ikdaman.global.auth.payload.Tokens;
import com.ikdaman.global.auth.token.AuthToken;
import com.ikdaman.global.auth.token.AuthTokenProvider;
import com.ikdaman.global.exception.BaseException;
import com.ikdaman.global.util.RedisService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.ikdaman.global.exception.ErrorCode.INVALID_REFRESH_TOKEN;

/**
 * 인증 서비스 구현체
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthTokenProvider authTokenProvider;
    private final RedisService redisService;

    // TODO: 프로바이더별 공통 코드 통합해서 작성

    @Value("${auth.refresh-token-validity}")
    private long refreshExpiry; // RefreshToken 만료일

    @Transactional
    public Tokens reissueToken(String accessToken, String refreshToken) {
        AuthToken token = authTokenProvider.convertAuthToken(refreshToken);
        if (!token.validate()) throw new BaseException(INVALID_REFRESH_TOKEN);

        String memberId = token.getTokenClaims().getSubject();

        // 저장된 RefreshToken과 비교
        String storeRefreshToken = redisService.getValues(memberId)
                .orElseThrow(() -> new BaseException(INVALID_REFRESH_TOKEN));
        if(!storeRefreshToken.equals(refreshToken)) {
            throw new BaseException(INVALID_REFRESH_TOKEN);
        }

        // 신규 토큰 발급
        AuthToken newAccessToken = authTokenProvider.createUserAppToken(memberId);
        AuthToken newRefreshToken = authTokenProvider.createRefreshToken(memberId);
        Tokens tokens = Tokens.builder()
                .accessToken(newAccessToken.getToken())
                .refreshToken(newRefreshToken.getToken())
                .build();
        redisService.setValuesWithTimeout(memberId, newRefreshToken.getToken(), refreshExpiry);

        return tokens;
    }
}
