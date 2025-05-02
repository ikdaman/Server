package com.ikdaman.global.config;

import com.ikdaman.global.auth.filter.JwtAuthenticationFilter;
import com.ikdaman.global.auth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthTokenProvider authTokenProvider;

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(authTokenProvider);

        http
                .authorizeHttpRequests(auth -> auth   // 요청에 관한 권한 체크
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // preflight 대응
                        .requestMatchers("/auth/**").permitAll() // "/auth/**"에 대한 접근을 인증 절차 없이 허용
                        .anyRequest().authenticated() // 그 외는 인증 필요
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // 동일 도메인 iframe 허용
                )
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable()) // csrf 비활성화
                .formLogin(formLogin -> formLogin.disable()) // 기본 로그인폼 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증 -> 세선도 사용 X
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);    // 커스텀 필터 등록하며, 기존에 지정된 필터에 앞서 실행

        return http.build();
    }
}
