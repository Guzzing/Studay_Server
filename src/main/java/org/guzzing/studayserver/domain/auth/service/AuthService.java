package org.guzzing.studayserver.domain.auth.service;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studayserver.domain.auth.service.dto.AuthLoginResult;
import org.guzzing.studayserver.domain.auth.service.dto.AuthLogoutResult;
import org.guzzing.studayserver.domain.auth.exception.TokenExpiredException;
import org.guzzing.studayserver.domain.auth.exception.TokenIsLogoutException;
import org.guzzing.studayserver.domain.auth.jwt.AuthToken;
import org.guzzing.studayserver.domain.auth.jwt.AuthTokenProvider;
import org.guzzing.studayserver.domain.auth.jwt.JwtTokenCache;
import org.guzzing.studayserver.domain.auth.jwt.LogoutCache;
import org.guzzing.studayserver.domain.auth.repository.LogoutTokenRepository;
import org.guzzing.studayserver.domain.auth.repository.RefreshTokenRepository;
import org.guzzing.studayserver.domain.auth.service.dto.AuthRefreshResult;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {

    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;
    private final LogoutTokenRepository logoutTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(AuthTokenProvider authTokenProvider,
                       MemberRepository memberRepository,
                       LogoutTokenRepository logoutTokenRepository,
                       RefreshTokenRepository refreshTokenRepository) {
        this.authTokenProvider = authTokenProvider;
        this.memberRepository = memberRepository;
        this.logoutTokenRepository = logoutTokenRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public AuthRefreshResult updateToken(AuthToken authToken, Long memberId) {
        Claims claims = authToken.getTokenClaims();
        String socialId = claims.getSubject();
        Member savedMember = memberRepository.getById(memberId);

        if (isNotExpiredRefreshToken(authToken.getToken())) {
            authToken = saveNewAccessTokenInfo(memberId, socialId, authToken.getToken());
        }

        return AuthRefreshResult.of(authToken.getToken(), memberId, savedMember.getNickName());
    }

    @Transactional
    public AuthLogoutResult logout(Long memberId, AuthToken authToken) {
        refreshTokenRepository.deleteById(memberId);
        logoutTokenRepository.save(LogoutCache.of(memberId, authToken.getToken()));

        return new AuthLogoutResult(true);
    }

    @Transactional(readOnly = true)
    public boolean isLogout(String accessToken) {
        Optional<LogoutCache> byAccessToken = logoutTokenRepository.findByAccessToken(accessToken);
        if (byAccessToken.isPresent()) {
            throw new TokenIsLogoutException(ErrorCode.IS_LOGOUT_TOKEN);
        }

        return false;
    }
    
    @Transactional
    public AuthToken saveNewAccessTokenInfo(Long memberId, String socialId, String accessToken) {
        AuthToken refreshToken = findRefreshToken(accessToken);
        AuthToken newAccessToken = authTokenProvider.createAccessToken(socialId, memberId);

        refreshTokenRepository.save(new JwtTokenCache(memberId, refreshToken.getToken(), newAccessToken.getToken()));

        return newAccessToken;
    }

    @Transactional
    public AuthToken saveAccessTokenCache(Long memberId, String socialId) {
        AuthToken newAccessToken = authTokenProvider.createAccessToken(socialId, memberId);
        AuthToken newRefreshToken = authTokenProvider.createRefreshToken();

        refreshTokenRepository.save(new JwtTokenCache(memberId, newRefreshToken.getToken(), newAccessToken.getToken()));

        return newAccessToken;
    }

    private boolean isNotExpiredRefreshToken(String accessToken) {
        return findRefreshToken(accessToken).isValidTokenClaims();
    }

    private AuthToken findRefreshToken(String accessToken) {
        JwtTokenCache jwtTokenCache = refreshTokenRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new TokenExpiredException(ErrorCode.EXPIRED_REFRESH_TOKEN));
        return authTokenProvider.convertAuthToken(jwtTokenCache.getRefreshToken());
    }

}
