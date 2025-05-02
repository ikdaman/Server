package com.ikdaman.global.auth.filter;

import com.ikdaman.global.auth.token.AuthToken;
import com.ikdaman.global.auth.token.AuthTokenProvider;
import com.ikdaman.global.auth.util.JwtHeaderUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;

    private static final List<String> NO_CHECK_URLS = List.of(
            "/auth/login",
            "/auth/reissue"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return NO_CHECK_URLS.contains(path);  // 토큰 검사 제외
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization"); // Authorization 헤더 꺼냄

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { // JWT 토큰 존재하는지 확인
            String tokenStr = JwtHeaderUtil.getAccessToken(request); // Bearer로 시작하는 값에서 Bearer를 제거한 accessToken(여기선 appToken) 반환

            AuthToken token = tokenProvider.createUserAppToken(tokenStr); //**

            if (token.validate()) { // token이 유효한지 확인
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication); // token에 존재하는 authentication 정보 삽입
            }

            filterChain.doFilter(request, response);
        }
    }
}
