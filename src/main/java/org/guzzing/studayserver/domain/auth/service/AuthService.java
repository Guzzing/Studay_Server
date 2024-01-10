package org.guzzing.studayserver.domain.auth.service;

import static org.guzzing.studayserver.global.error.response.ErrorCode.EXPIRED_REFRESH_TOKEN;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studayserver.domain.auth.exception.TokenExpiredException;
import org.guzzing.studayserver.domain.auth.jwt.AuthToken;
import org.guzzing.studayserver.domain.auth.jwt.AuthTokenProvider;
import org.guzzing.studayserver.domain.auth.repository.JwtCacheRepository;
import org.guzzing.studayserver.domain.auth.service.dto.AuthLogoutResult;
import org.guzzing.studayserver.domain.auth.service.dto.AuthRefreshResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthService {

    private final AuthTokenProvider authTokenProvider;
    private final JwtCacheRepository jwtCacheRepository;

    public AuthService(
            AuthTokenProvider authTokenProvider,
            JwtCacheRepository refreshTokenCacheRepository
    ) {
        this.authTokenProvider = authTokenProvider;
        this.jwtCacheRepository = refreshTokenCacheRepository;
    }

    @Transactional
    public AuthToken saveAccessToken(Long memberId, String socialId) {
        AuthToken accessToken = authTokenProvider.createAccessToken(socialId, memberId);
        AuthToken refreshToken = authTokenProvider.createRefreshToken();

        jwtCacheRepository.save(accessToken.getToken(), refreshToken.getToken());

        return accessToken;
    }

    @Transactional
    public AuthRefreshResult updateAccessToken(AuthToken accessToken, Long memberId) {
        Claims claims = accessToken.getTokenClaims();
        String socialId = claims.getSubject();

        validateRefreshToken(accessToken.getToken());

        AuthToken newAccessToken = saveAccessToken(memberId, socialId);

        return AuthRefreshResult.of(newAccessToken.getToken(), memberId);
    }

    @Transactional
    public AuthLogoutResult logout(AuthToken authToken) {
        jwtCacheRepository.delete(authToken.getToken());

        return new AuthLogoutResult(true);
    }

    @Transactional(readOnly = true)
    public boolean isLogout(String accessToken) {
        return jwtCacheRepository.findRefreshToken(accessToken)
                .isEmpty();
    }

    private void validateRefreshToken(String accessToken) {
        boolean isValidRefreshToken = findRefreshToken(accessToken).isValidTokenClaims();

        if (!isValidRefreshToken) {
            throw new TokenExpiredException(EXPIRED_REFRESH_TOKEN);
        }
    }

    private AuthToken findRefreshToken(String accessToken) {
        String refreshToken = jwtCacheRepository.findRefreshToken(accessToken)
                .orElseThrow(() -> new TokenExpiredException(EXPIRED_REFRESH_TOKEN));

        return authTokenProvider.convertAuthToken(refreshToken);
    }

}
