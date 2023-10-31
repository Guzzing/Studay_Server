package org.guzzing.studay.domain.auth.client;

import org.guzzing.studay.global.error.response.ErrorCode;
import org.guzzing.studay.domain.auth.dto.KakaoUserResponse;
import org.guzzing.studay.domain.auth.exception.TokenValidFailedException;
import org.guzzing.studay.domain.member.model.Member;
import org.guzzing.studay.domain.member.model.vo.MemberProvider;
import org.guzzing.studay.domain.member.model.vo.RoleType;
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

        KakaoUserResponse kakaoUserResponse = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.set("Authorization", accessToken))
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> Mono.error(new TokenValidFailedException(ErrorCode.UNAUTHORIZED_TOKEN)))
                .onStatus(status -> status.is5xxServerError(), response -> Mono.error(new TokenValidFailedException(ErrorCode.OAUTH_CLIENT_SERVER_ERROR)))
                .bodyToMono(KakaoUserResponse.class)
                .block();

        return Member.of(
                kakaoUserResponse.getProperties().getNickname(),
                String.valueOf(kakaoUserResponse.getId()),
                MemberProvider.KAKAO,
                RoleType.USER
        );
    }

}
