package org.guzzing.studayserver.domain.auth.service;

import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studayserver.domain.auth.exception.TokenExpiredException;
import org.guzzing.studayserver.domain.auth.exception.TokenIsLogoutException;
import org.guzzing.studayserver.domain.auth.jwt.AuthToken;
import org.guzzing.studayserver.domain.auth.jwt.AuthTokenProvider;
import org.guzzing.studayserver.domain.auth.repository.LogoutTokenRepository;
import org.guzzing.studayserver.domain.auth.repository.RefreshTokenRepository;
import org.guzzing.studayserver.domain.auth.service.dto.AuthLogoutResult;
import org.guzzing.studayserver.domain.auth.service.dto.AuthRefreshResult;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthService {

    private final AuthTokenProvider authTokenProvider;
    private final JwtCacheRepository jwtCacheRepository;
    private final LogoutTokenRepository logoutTokenRepository;

    public AuthService(
            AuthTokenProvider authTokenProvider,
            JwtCacheRepository refreshTokenCacheRepository,
            LogoutTokenRepository logoutTokenCacheRepository
    ) {
        this.authTokenProvider = authTokenProvider;
        this.jwtCacheRepository = refreshTokenCacheRepository;
        this.logoutTokenRepository = logoutTokenCacheRepository;
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
    public void withdraw(AuthToken authToken) {
        jwtCacheRepository.delete(authToken.getToken());
    }

    public AuthLogoutResult logout(AuthToken authToken) {
        refreshTokenRepository.deleteByAccessToken(authToken.getToken());
        logoutTokenRepository.save(authToken.getToken());

        return new AuthLogoutResult(true);
    }

    @Transactional(readOnly = true)
    public boolean isLogout(String accessToken) {
        Optional<String> logoutToken = logoutTokenRepository.findByLogoutToken(accessToken);
        if (logoutToken.isPresent()) {
            throw new TokenIsLogoutException(ErrorCode.IS_LOGOUT_TOKEN);
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
