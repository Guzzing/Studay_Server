package org.guzzing.studayserver.domain.auth.service;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studayserver.domain.auth.service.dto.AuthLogoutResult;
import org.guzzing.studayserver.domain.auth.exception.TokenExpiredException;
import org.guzzing.studayserver.domain.auth.exception.TokenIsLogoutException;
import org.guzzing.studayserver.domain.auth.jwt.AuthToken;
import org.guzzing.studayserver.domain.auth.jwt.AuthTokenProvider;
import org.guzzing.studayserver.domain.auth.jwt.JwtToken;
import org.guzzing.studayserver.domain.auth.jwt.logout.LogoutToken;
import org.guzzing.studayserver.domain.auth.repository.LogoutTokenRepository;
import org.guzzing.studayserver.domain.auth.repository.RefreshTokenRepository;
import org.guzzing.studayserver.domain.auth.service.dto.AuthRefreshResult;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {

    private final AuthTokenProvider authTokenProvider;
    private final LogoutTokenRepository logoutTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(AuthTokenProvider authTokenProvider,
                       LogoutTokenRepository logoutTokenRepository,
                       RefreshTokenRepository refreshTokenRepository) {
        this.authTokenProvider = authTokenProvider;
        this.logoutTokenRepository = logoutTokenRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public AuthRefreshResult updateToken(AuthToken authToken, Long memberId) {
        Claims claims = authToken.getTokenClaims();
        String socialId = claims.getSubject();

        if (isNotExpiredRefreshToken(authToken.getToken())) {
            authToken = saveNewAccessTokenInfo(memberId, socialId, authToken.getToken());
        }

        return AuthRefreshResult.of(authToken.getToken(), memberId);
    }

    @Transactional
    public AuthLogoutResult logout(AuthToken authToken) {
        refreshTokenRepository.deleteById(authToken.getToken());
        logoutTokenRepository.save(LogoutToken.of(authToken.getToken()));

        return new AuthLogoutResult(true);
    }

    @Transactional(readOnly = true)
    public boolean isLogout(String accessToken) {
        Optional<LogoutToken> byAccessToken = logoutTokenRepository.findById(accessToken);
        if (byAccessToken.isPresent()) {
            throw new TokenIsLogoutException(ErrorCode.IS_LOGOUT_TOKEN);
        }

        return false;
    }

    @Transactional
    public AuthToken saveNewAccessTokenInfo(Long memberId, String socialId, String accessToken) {
        AuthToken refreshToken = findRefreshToken(accessToken);
        AuthToken newAccessToken = authTokenProvider.createAccessToken(socialId, memberId);

        refreshTokenRepository.save(new JwtToken(refreshToken.getToken(), newAccessToken.getToken()));

        return newAccessToken;
    }

    @Transactional
    public AuthToken saveAccessTokenCache(Long memberId, String socialId) {
        AuthToken newAccessToken = authTokenProvider.createAccessToken(socialId, memberId);
        AuthToken newRefreshToken = authTokenProvider.createRefreshToken();

        refreshTokenRepository.save(new JwtToken(newRefreshToken.getToken(), newAccessToken.getToken()));

        return newAccessToken;
    }

    private boolean isNotExpiredRefreshToken(String accessToken) {
        return findRefreshToken(accessToken).isValidTokenClaims();
    }

    private AuthToken findRefreshToken(String accessToken) {
        JwtToken jwtToken = refreshTokenRepository.findById(accessToken)
                .orElseThrow(() -> new TokenExpiredException(ErrorCode.EXPIRED_REFRESH_TOKEN));
        return authTokenProvider.convertAuthToken(jwtToken.getRefreshToken());
    }

}
