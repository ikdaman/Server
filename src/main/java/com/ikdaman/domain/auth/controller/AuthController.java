package com.ikdaman.domain.auth.controller;

import com.ikdaman.domain.auth.model.AuthReq;
import com.ikdaman.domain.auth.model.AuthRes;
import com.ikdaman.domain.auth.service.AuthService;
import com.ikdaman.domain.auth.service.OAuthService;
import com.ikdaman.global.auth.payload.Tokens;
import com.ikdaman.global.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

import static com.ikdaman.global.exception.ErrorCode.INVALID_SOCIAL_PROVIDER;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Map<String, OAuthService> socialLoginServices;
    private final AuthService authService;

    /**
     * 소셜 로그인
     * @param dto
     * @param socialToken
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<AuthRes> socialLogin(@RequestBody AuthReq dto,
                                               @RequestHeader("social-access-token") String socialToken) {

        String provider = dto.getProvider().toLowerCase();
        OAuthService oAuthService = socialLoginServices.get(provider);
        if (oAuthService == null) {
            throw new BaseException(INVALID_SOCIAL_PROVIDER);
        }

        AuthRes res = oAuthService.login(dto, socialToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", res.getAccessToekn());
        headers.add("refresh-token", res.getRefreshToken());

        res = AuthRes.builder()
                .nickname(res.getNickname())
                .build();

        return ResponseEntity.ok()
                .headers(headers)
                .body(res);
    }

    /**
     * Access Token 재발급
     * @param accessToken
     * @param refreshToken
     * @return
     */
    @PostMapping("/reissue")
    public ResponseEntity reissueToken(@RequestHeader("Authorization") String accessToken,
                                       @RequestHeader("refresh-token") String refreshToken) {

        Tokens tokens = authService.reissueToken(accessToken, refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", tokens.getAccessToken());
        headers.add("refresh-token", tokens.getRefreshToken());

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    /**
     * 로그아웃
     * @param request
     * @return
     */
    @DeleteMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        UUID memberId = (UUID) request.getAttribute("memberId");
        authService.logout(memberId);

        return ResponseEntity.status(HttpStatus.RESET_CONTENT)
                .build();
    }
} 