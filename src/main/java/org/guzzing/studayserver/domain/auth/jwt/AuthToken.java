package org.guzzing.studayserver.domain.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthToken {

    private static final String AUTHORITIES_KEY = "role";

    @Getter
    private final String token;
    private final Key key;

    AuthToken(String token, Key key) {
        this.token = token;
        this.key = key;
    }

    @Builder
    AuthToken(String socialId, String role, Long memberId, Date expiry, Key key) {
        this.token = createAccessToken(socialId, role, memberId, expiry, key);
        this.key = key;
    }

    AuthToken(Date expiry, Key key) {
        this.token = createRefreshToken(expiry, key);
        this.key = key;
    }

    private String createAccessToken(String socialId, String role, Long id, Date expiry, Key key) {
        return Jwts
                .builder()
                .setSubject(socialId)
                .claim(AUTHORITIES_KEY, role)
                .claim("memberId", id)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    private String createRefreshToken(Date expiry, Key key) {
        Claims claims = Jwts
                .claims();

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValidTokenClaims() {
        Optional<Object> claims = Optional.empty();
        try {
            claims = Optional.ofNullable(getTokenClaims());
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        } catch (Exception e) {
            return false;
        }
        return claims.isPresent();
    }

    public Claims getTokenClaims() {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        return jwtParser.parseClaimsJws(token).getBody();
    }

}
