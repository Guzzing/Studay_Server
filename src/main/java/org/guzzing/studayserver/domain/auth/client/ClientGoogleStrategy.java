package org.guzzing.studayserver.domain.auth.client;

import java.util.Objects;
import org.guzzing.studayserver.domain.auth.client.dto.GoogleUser;
import org.guzzing.studayserver.domain.auth.exception.TokenValidFailedException;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.global.common.auth.OAuth2Provider;
import org.guzzing.studayserver.global.common.auth.RoleType;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ClientGoogleStrategy implements ClientStrategy {

    private final WebClient webClient;

    public ClientGoogleStrategy(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Member getUserData(String accessToken) {

        GoogleUser googleUser = webClient.get()
                .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                .headers(h -> h.set("Authorization", accessToken))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new TokenValidFailedException(ErrorCode.UNAUTHORIZED_TOKEN)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new TokenValidFailedException(ErrorCode.OAUTH_CLIENT_SERVER_ERROR)))
                .bodyToMono(GoogleUser.class)
                .block();

        return Member.of(
                Objects.requireNonNull(googleUser)
                        .getName(),
                googleUser.getSub(),
                OAuth2Provider.GOOGLE,
                RoleType.USER
        );
    }

}
