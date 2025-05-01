package com.ikdaman.domain.auth.controller;

import com.ikdaman.domain.auth.model.AuthReq;
import com.ikdaman.domain.auth.model.AuthRes;
import com.ikdaman.domain.auth.service.SocialLoginService;
import com.ikdaman.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.ikdaman.global.exception.ErrorCode.INVALID_SOCIAL_PROVIDER;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Map<String, SocialLoginService> socialLoginServices;

    /**
     * 소셜 로그인
     * @param dto
     * @param socialToken
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<AuthRes> socialLogin(
            @RequestBody AuthReq dto,
            @RequestHeader("social-access-token") String socialToken) {

        String provider = dto.getProvider().toLowerCase();
        SocialLoginService loginService = socialLoginServices.get(provider);
        if (loginService == null) {
            throw new BaseException(INVALID_SOCIAL_PROVIDER);
        }

        AuthRes res = loginService.login(dto, socialToken);
        return ResponseEntity.ok(res);
    }
} 