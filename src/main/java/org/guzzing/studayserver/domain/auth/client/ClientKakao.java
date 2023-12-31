package org.guzzing.studayserver.domain.auth.client;

import java.util.Objects;
import org.guzzing.studayserver.domain.auth.client.dto.KakaoUser;
import org.guzzing.studayserver.domain.auth.exception.TokenValidFailedException;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ClientKakao implements ClientProxy {

    private final WebClient webClient;

    public ClientKakao(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Member getUserData(String accessToken) {

        KakaoUser kakaoUser = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.set("Authorization", accessToken))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new TokenValidFailedException(ErrorCode.UNAUTHORIZED_TOKEN)))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new TokenValidFailedException(ErrorCode.OAUTH_CLIENT_SERVER_ERROR)))
                .bodyToMono(KakaoUser.class)
                .block();

        return Member.of(
                Objects.requireNonNull(kakaoUser)
                        .getProperties()
                        .getNickname(),
                String.valueOf(kakaoUser.getId()),
                MemberProvider.KAKAO,
                RoleType.USER
        );
    }

}
