package org.guzzing.studay.domain.auth.service;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studay.domain.auth.dto.AuthResponse;
import org.guzzing.studay.domain.auth.jwt.AuthToken;
import org.guzzing.studay.domain.auth.jwt.AuthTokenProvider;
import org.guzzing.studay.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenService refreshTokenService;

    public AuthService(AuthTokenProvider authTokenProvider, MemberRepository memberRepository, RefreshTokenService refreshTokenService) {
        this.authTokenProvider = authTokenProvider;
        this.memberRepository = memberRepository;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponse updateToken(AuthToken authToken) {

        if (authToken.isValidTokenClaims()) {
            Claims claims = authToken.getTokenClaims();
            String socialId = claims.getSubject();
            Long memberId = memberRepository.findBySocialId(socialId).getId();

            if (refreshTokenService.isExpiredRefreshToken(authToken.getToken())) {
                authToken = refreshTokenService.saveNewAccessTokenInfo(memberId, socialId, authToken.getToken());
            }
        }

        return AuthResponse.builder()
                .appToken(authToken.getToken())
                .build();
    }

}
