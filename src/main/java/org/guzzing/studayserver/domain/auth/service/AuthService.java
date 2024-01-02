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
import org.guzzing.studayserver.domain.auth.service.dto.AuthWithdrawResult;
import org.guzzing.studayserver.domain.member.service.MemberFacade;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthService {

    private final AuthTokenProvider authTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final LogoutTokenRepository logoutTokenRepository;
    private final MemberFacade memberFacade;

    public AuthService(
            AuthTokenProvider authTokenProvider,
            RefreshTokenRepository refreshTokenCacheRepository,
            LogoutTokenRepository logoutTokenCacheRepository,
            MemberFacade memberFacade
    ) {
        this.authTokenProvider = authTokenProvider;
        this.refreshTokenRepository = refreshTokenCacheRepository;
        this.logoutTokenRepository = logoutTokenCacheRepository;
        this.memberFacade = memberFacade;
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
    public AuthWithdrawResult withdraw(AuthToken authToken, Long memberId) {
        memberFacade.removeMember(memberId);

        refreshTokenRepository.deleteByAccessToken(authToken.getToken());
        logoutTokenRepository.save(authToken.getToken());

        return new AuthWithdrawResult(true);
    }

    @Transactional
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

        return false;
    }

    @Transactional
    public AuthToken saveNewAccessTokenInfo(Long memberId, String socialId, String accessToken) {
        AuthToken refreshToken = findRefreshToken(accessToken);
        AuthToken newAccessToken = authTokenProvider.createAccessToken(socialId, memberId);

        refreshTokenRepository.save(newAccessToken.getToken(), refreshToken.getToken());

        return newAccessToken;
    }

    @Transactional
    public AuthToken saveAccessTokenCache(Long memberId, String socialId) {
        AuthToken newAccessToken = authTokenProvider.createAccessToken(socialId, memberId);
        AuthToken newRefreshToken = authTokenProvider.createRefreshToken();

        refreshTokenRepository.save(newAccessToken.getToken(), newRefreshToken.getToken());

        return newAccessToken;
    }

    private boolean isNotExpiredRefreshToken(String accessToken) {
        return findRefreshToken(accessToken).isValidTokenClaims();
    }

    private AuthToken findRefreshToken(String accessToken) {
        String refreshToken = refreshTokenRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new TokenExpiredException(ErrorCode.EXPIRED_REFRESH_TOKEN));
        return authTokenProvider.convertAuthToken(refreshToken);
    }

    public List<Map.Entry<String, String>> findAll() {
        return refreshTokenRepository.findAll();
    }

}
