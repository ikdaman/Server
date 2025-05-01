package com.ikdaman.global.auth.token;

import com.ikdaman.global.auth.enumerate.RoleType;
import com.ikdaman.global.exception.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.ikdaman.global.exception.ErrorCode.FAILED_GENERATE_APP_TOKEN;

@Slf4j
@Component
public class AuthTokenProvider {
    @Value("${auth.access-token-validity}")
    private String expiry; // token 만료일

    private final Key key;
    private static final String AUTHORITIES_KEY = "role"; // getAuthentication에서 사용자 권한 체크 위해

    //생성자
    public AuthTokenProvider(@Value("${auth.tokenSecret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 추후 roleType 추가 시 interface 역할 하기 위해 생성
    // role: USER, ADMIN ..
    public AuthToken createToken(String id, RoleType roleType, String expiry) {
        Date expiryDate = getExpiryDate(expiry);
        return new AuthToken(id, roleType, expiryDate, key);
    }

    // USER에 대한 AccessToken(여기선 appToken) 생성
    public AuthToken createUserAppToken(String id) {
        return createToken(id, RoleType.USER, expiry);
    }

    // String to AuthToken
    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    // String to Date
    public static Date getExpiryDate(String expiry) {
        return new Date(System.currentTimeMillis() + Long.parseLong(expiry));
    }


    public Authentication getAuthentication(AuthToken authToken) {

        if (authToken.validate()) {

            Claims claims = authToken.getTokenClaims();

            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            User principal = new User(claims.getSubject(), "", authorities);
            // 사실상 principal에 저장되는 값은 socialId값과 role뿐 (소셜 로그인만 사용하여 password는 저장하지 않아 ""로 넣음)
            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } else {
            throw new BaseException(FAILED_GENERATE_APP_TOKEN);
        }
    }
}
