package org.guzzing.studayserver.domain.auth.listener;

import jakarta.servlet.http.HttpServletRequest;
import org.guzzing.studayserver.domain.auth.jwt.AuthToken;
import org.guzzing.studayserver.domain.auth.jwt.AuthTokenProvider;
import org.guzzing.studayserver.domain.auth.jwt.JwtHeaderUtil;
import org.guzzing.studayserver.domain.auth.service.AuthService;
import org.guzzing.studayserver.domain.member.event.WithdrawEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class WithdrawAuthTokenListener {

    private final AuthService authService;
    private final AuthTokenProvider authTokenProvider;

    public WithdrawAuthTokenListener(AuthService authService, AuthTokenProvider authTokenProvider) {
        this.authService = authService;
        this.authTokenProvider = authTokenProvider;
    }

    @Async
    @TransactionalEventListener
    public void withdrawAuthToken(WithdrawEvent event) {
        HttpServletRequest request = event.getRequest();
        String accessToken = JwtHeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(accessToken);

        authService.withdraw(authToken);
    }

}
