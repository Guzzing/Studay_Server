package org.guzzing.studayserver.domain.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenProvider {

    @Value("${app.auth.accessTokenExpiry}")
    private String accessTokenExpiry;

    @Value("${app.auth.refreshTokenExpiry}")
    private String refreshTokenExpiry;

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(@Value("${app.auth.tokenSecret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public AuthToken createAccessToken(String id, Long memberId) {
        Date accessExpiryDate = getExpiryDate(accessTokenExpiry);

        return AuthToken.builder()
                .socialId(id)
                .memberId(memberId)
                .role("ROLE_USER")
                .expiry(accessExpiryDate)
                .key(key)
                .build();
    }

    public AuthToken createRefreshToken() {
        Date accessExpiryDate = getExpiryDate(refreshTokenExpiry);
        return new AuthToken(accessExpiryDate, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public static Date getExpiryDate(String expiry) {
        return new Date(System.currentTimeMillis() + Long.parseLong(expiry));
    }

    public Authentication getAuthentication(AuthToken authToken) {

        Claims claims = authToken.getTokenClaims();

        List<SimpleGrantedAuthority> authorities = Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                .map(SimpleGrantedAuthority::new)
                .toList();

        int memberId = (int) claims.get("memberId");

        CustomUser customUser = CustomUser.builder()
                .socialId(claims.getSubject())
                .memberId((long) memberId)
                .authorities(authorities)
                .build();

        return new UsernamePasswordAuthenticationToken(customUser, authToken, authorities);
    }

}
