package com.ikdaman.global.auth.client;

import com.ikdaman.global.auth.payload.KakaoUserRes;
import com.ikdaman.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.ikdaman.global.exception.ErrorCode.FAILED_GENERATE_APP_TOKEN;

@Component
@RequiredArgsConstructor
public class ClientKakao {

    private final WebClient webClient;

    public String getUserData(String accessToken) {
        KakaoUserRes kakaoUserRes = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me") // Kakao의 유저 정보 받아오는 url
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
//                .headers(h -> h.setBearerAuth(accessToken)) // JWT 토큰을 Bearer 토큰으로 지정
                .retrieve()
                // onStatus <- error handling
                .onStatus(status -> status.is4xxClientError(), response
                        -> Mono.error(new BaseException(FAILED_GENERATE_APP_TOKEN)))
                .onStatus(status -> status.is5xxServerError(), response
                        -> Mono.error(new BaseException(FAILED_GENERATE_APP_TOKEN)))
                .bodyToMono(KakaoUserRes.class) // Kakao의 유저 정보를 넣을 DTO 클래스
                .block();

        return String.valueOf(kakaoUserRes.getId());
    }
}
