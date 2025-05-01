package com.ikdaman.domain.auth.service;

import com.ikdaman.domain.auth.model.AuthReq;
import com.ikdaman.domain.auth.model.AuthRes;
import com.ikdaman.domain.member.entity.Member;
import com.ikdaman.domain.member.repository.MemberRepository;
import com.ikdaman.global.auth.client.ClientKakao;
import com.ikdaman.global.auth.token.AuthToken;
import com.ikdaman.global.auth.token.AuthTokenProvider;
import com.ikdaman.global.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ikdaman.global.exception.ErrorCode.INVALID_SOCIAL_ACCESS_TOKEN;

@Service("kakao")
@RequiredArgsConstructor
public class KakaoAuthService implements SocialLoginService {

    private final ClientKakao clientKakao;
    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public AuthRes login(AuthReq dto, String socialAccessToken) {

        // 1. 소셜 accessToken을 통해 Kakao userId 가져오기
        String checkProviderId = clientKakao.getUserData(socialAccessToken);

        // 2. 요청으로 들어온 providerId와 비교해서 검증
        if (!dto.getProviderId().equals(checkProviderId)) throw new BaseException(INVALID_SOCIAL_ACCESS_TOKEN);

        // 3. 신규 토큰 생성
        AuthToken appToken = authTokenProvider.createUserAppToken(checkProviderId);
        // TODO: 신규 토큰

        // 4. 기존 회원 조회
        Member member = memberRepository.findBySocialTypeAndProviderId(Member.SocialType.KAKAO, checkProviderId)
                .orElseGet(() -> {
                    // TODO: 랜덤 닉네임 생성
                    String nickname = "user_" + checkProviderId;

                    // 유저 정보 저장
                    Member newMember = Member.builder()
                            .socialType(Member.SocialType.KAKAO)
                            .providerId(checkProviderId)
                            .nickname(nickname)
                            .build();
                    return memberRepository.save(newMember);
                });

        // 카카오 토큰 검증, 사용자 정보 가져오기 로직 작성
        return AuthRes.builder()
                .appToken(appToken.getToken())
                .nickname(member.getNickname())
                .build();
    }
}
