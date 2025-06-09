package com.ikdaman.global.auth.token;

import com.ikdaman.global.auth.enumerate.RoleType;
import com.ikdaman.global.exception.BaseException;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

import static com.ikdaman.global.exception.ErrorCode.EXPIRED_ACCESS_TOKEN;
import static com.ikdaman.global.exception.ErrorCode.INVALID_ACCESS_TOKEN;
import static com.ikdaman.global.exception.ErrorCode.INVALID_ACCESS_TOKEN_FORMAT;
import static com.ikdaman.global.exception.ErrorCode.INVALID_ACCESS_TOKEN_SIGNATURE;
import static com.ikdaman.global.exception.ErrorCode.UNSUPPORTED_ACCESS_TOKEN;

@Slf4j
@RequiredArgsConstructor
public class AuthToken {

    @Getter
    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    AuthToken(String id, RoleType roleType, Date expiry, Key key) {
        String role = roleType.toString(); // USER, ADMIN
        this.key = key;
        this.token = createAuthToken(id, role, expiry);
    }

    // AccessToken(appToken) 생성
    private String createAuthToken(String id, String role, Date expiry) {
        return Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    // AccessToken(appToken) 유효한지 체크
    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody(); // token의 Body가 다음의 exception들로 인해 유효하지 않으면 각각의 로그를 콘솔에 출력

        } catch (SecurityException e) {
            throw new BaseException(INVALID_ACCESS_TOKEN_SIGNATURE);
        } catch (MalformedJwtException e) {
            // 처음 로그인(/auth/kakao) 할 때, AccessToken(여기선 appToken) 없이 접근해도 token validate 체크
            // -> exception 터트리지 않고 catch로 잡아줌
            throw new BaseException(INVALID_ACCESS_TOKEN_FORMAT);
        } catch (ExpiredJwtException e) {
            throw new BaseException(EXPIRED_ACCESS_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new BaseException(UNSUPPORTED_ACCESS_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new BaseException(INVALID_ACCESS_TOKEN);
        }
    }
}