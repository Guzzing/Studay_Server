package org.guzzing.studayserver.domain.auth.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import org.guzzing.studayserver.domain.child.model.NickName;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record KakaoUser(
        Long id,
        Properties properties,
        KakaoAccount kakaoAccount
) {

    @Getter
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Properties {

        private NickName nickname;

        public Properties(NickName nickname) {
            this.nickname = nickname;
        }

    }

    @Getter
    public static class KakaoAccount {

        private String email;

        public KakaoAccount(String email) {
            this.email = email;
        }
    }

}
