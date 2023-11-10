package org.guzzing.studayserver.domain.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.guzzing.studayserver.domain.auth.controller.dto.AuthLoginResponse;
import org.guzzing.studayserver.domain.auth.controller.dto.AuthRefreshResponse;
import org.guzzing.studayserver.domain.auth.service.dto.AuthLoginResult;
import org.guzzing.studayserver.domain.auth.service.dto.AuthLogoutResult;
import org.guzzing.studayserver.domain.auth.jwt.AuthToken;
import org.guzzing.studayserver.domain.auth.jwt.AuthTokenProvider;
import org.guzzing.studayserver.domain.auth.jwt.JwtHeaderUtil;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.auth.service.AuthService;
import org.guzzing.studayserver.domain.auth.service.ClientService;
import org.guzzing.studayserver.domain.auth.service.dto.AuthRefreshResult;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String HEADER_AUTHORIZATION = "Authorization";

    private final AuthTokenProvider authTokenProvider;
    private final ClientService clientService;
    private final AuthService authService;


    public AuthController(AuthTokenProvider authTokenProvider, ClientService clientService, AuthService authService) {
        this.authTokenProvider = authTokenProvider;
        this.clientService = clientService;
        this.authService = authService;
    }

    @PostMapping(value = "/kakao")
    public ResponseEntity<AuthLoginResponse> kakaoAuthRequest(HttpServletRequest request) {
        AuthLoginResult authLoginResult = clientService.login(
                MemberProvider.KAKAO.name(),
                request.getHeader(HEADER_AUTHORIZATION)
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(AuthLoginResponse.from(authLoginResult));
    }

    @PostMapping(value = "/google")
    public ResponseEntity<AuthLoginResponse> googleAuthRequest(HttpServletRequest request) {
        AuthLoginResult authLoginResult = clientService.login(
                MemberProvider.GOOGLE.name(),
                request.getHeader(HEADER_AUTHORIZATION)
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(AuthLoginResponse.from(authLoginResult));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthRefreshResponse> refreshToken(
            HttpServletRequest request,
            @MemberId Long memberId) {

        String appToken = JwtHeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(appToken);

        AuthRefreshResult authRefreshResult = authService.updateToken(authToken, memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(AuthRefreshResponse.from(authRefreshResult));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<AuthLogoutResult> logout(
            HttpServletRequest request) {
        String appToken = JwtHeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(appToken);

        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.logout(authToken));
    }

}
