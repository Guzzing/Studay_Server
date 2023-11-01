package org.guzzing.studayserver.domain.auth.client;

import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.guzzing.studayserver.domain.auth.dto.GoogleUserResponse;
import org.guzzing.studayserver.domain.auth.exception.TokenValidFailedException;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ClientGoogle implements ClientProxy {

    private final WebClient webClient;

    public ClientGoogle(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Member getUserData(String accessToken) {

        GoogleUserResponse googleUserResponse = webClient.get()
                .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                .headers(h -> h.set("Authorization", accessToken))
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> Mono.error(new TokenValidFailedException(ErrorCode.UNAUTHORIZED_TOKEN)))
                .onStatus(status -> status.is5xxServerError(), response -> Mono.error(new TokenValidFailedException(ErrorCode.OAUTH_CLIENT_SERVER_ERROR)))
                .bodyToMono(GoogleUserResponse.class)
                .block();

        System.out.println("googleUserResponse:" + googleUserResponse);

        return Member.of(
                googleUserResponse.getName(),
                googleUserResponse.getSub(),
                MemberProvider.GOOGLE,
                RoleType.USER
        );
    }

}
