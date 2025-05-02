package com.ikdaman.domain.auth.service;

import com.ikdaman.domain.auth.model.AuthReq;
import com.ikdaman.domain.auth.model.AuthRes;
import com.ikdaman.domain.member.entity.Member;
import com.ikdaman.domain.member.repository.MemberRepository;
import com.ikdaman.global.auth.client.ClientNaver;
import com.ikdaman.global.auth.token.AuthToken;
import com.ikdaman.global.auth.token.AuthTokenProvider;
import com.ikdaman.global.exception.BaseException;
import com.ikdaman.global.util.RandomNickname;
import com.ikdaman.global.util.RedisService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.ikdaman.global.exception.ErrorCode.NOT_MATCH_TOKEN_PROVIDER;

@Service("naver")
@RequiredArgsConstructor
public class NaverAuthService implements OAuthService {

    private final ClientNaver clientNaver;
    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;
    private final RedisService redisService;
    private final RandomNickname randomNickname;

    @Value("${auth.refresh-token-validity}")
    private long refreshExpiry; // RefreshToken 만료일

    @Override
    @Transactional
    public AuthRes login(AuthReq dto, String socialAccessToken) {

        // 1. 소셜 accessToken을 통해 Naver userId 가져오기
        String checkProviderId = clientNaver.getUserData(socialAccessToken);

        // 2. 요청으로 들어온 providerId와 비교해서 검증
        if (!dto.getProviderId().equals(checkProviderId)) throw new BaseException(NOT_MATCH_TOKEN_PROVIDER);

        // 3. 기존 회원 조회
        Member member = memberRepository.findBySocialTypeAndProviderId(Member.SocialType.NAVER, checkProviderId)
                .orElseGet(() -> {
                    String nickname = randomNickname.generate();

                    // 유저 정보 저장
                    Member newMember = Member.builder()
                            .socialType(Member.SocialType.NAVER)
                            .providerId(checkProviderId)
                            .nickname(nickname)
                            .build();
                    return memberRepository.save(newMember);
                });

        // 3. 신규 토큰 생성 및 저장
        AuthToken accessToken = authTokenProvider.createUserAppToken(String.valueOf(member.getMemberId()));
        AuthToken refreshToken = authTokenProvider.createRefreshToken(String.valueOf(member.getMemberId()));
        redisService.setValuesWithTimeout(String.valueOf(member.getMemberId()), refreshToken.getToken(), refreshExpiry);

        return AuthRes.builder()
                .accessToekn(accessToken.getToken())
                .refreshToken(refreshToken.getToken())
                .nickname(member.getNickname())
                .build();
    }
}
