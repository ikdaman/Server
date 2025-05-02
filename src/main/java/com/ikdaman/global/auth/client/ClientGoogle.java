package com.ikdaman.global.auth.client;

import com.ikdaman.global.auth.payload.OAuthUserRes;
import com.ikdaman.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.ikdaman.global.exception.ErrorCode.GOOGLE_SERVER_ERROR;
import static com.ikdaman.global.exception.ErrorCode.INVALID_SOCIAL_ACCESS_TOKEN;

@Component
@RequiredArgsConstructor
public class ClientGoogle {

    private final WebClient webClient;

    // TODO: HttpHeaders.AUTHORIZATION 사용하도록 변경
    public String getUserData(String accessToken) {
        OAuthUserRes OAuthUserRes = webClient.get()
                .uri("https://www.googleapis.com/oauth2/v2/userinfo") // Google의 유저 정보 받아오는 url
                .header("Authorization", "Bearer " + accessToken)
//                .headers(h -> h.setBearerAuth("U-" + accessToken)) // 임시로 발급받은 사용자 토큰으로 접근
                .retrieve()
                // onStatus <- error handling
                .onStatus(status -> status.is4xxClientError(), response
                        -> Mono.error(new BaseException(INVALID_SOCIAL_ACCESS_TOKEN)))
                .onStatus(status -> status.is5xxServerError(), response
                        -> Mono.error(new BaseException(GOOGLE_SERVER_ERROR)))
                .bodyToMono(OAuthUserRes.class) // 유저 정보를 넣을 DTO 클래스
                .block();

        return String.valueOf(OAuthUserRes.getId());
    }
}
