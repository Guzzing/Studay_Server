package org.guzzing.studayserver.domain.auth.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import org.guzzing.studayserver.domain.child.model.NickName;


@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoUser {

    private Long id;
    private Properties properties;
    private KakaoAccount kakaoAccount;

    public KakaoUser() {
    }

    public KakaoUser(Long id, Properties properties, KakaoAccount kakaoAccount) {
        this.id = id;
        this.properties = properties;
        this.kakaoAccount = kakaoAccount;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Properties {

        private NickName nickname;

        public Properties() {
        }

        public Properties(NickName nickname) {
            this.nickname = nickname;
        }

    }

    @Getter
    public static class KakaoAccount {

        private String email;

        public KakaoAccount() {
        }

        public KakaoAccount(String email) {
            this.email = email;
        }

    }

}
