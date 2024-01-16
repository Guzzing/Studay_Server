package org.guzzing.studayserver.domain.auth.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import org.guzzing.studayserver.domain.member.model.vo.NickName;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleUser {

    private String sub;
    private String email;
    private NickName name;

    public GoogleUser() {
    }

    public GoogleUser(String sub, String email, NickName name) {
        this.sub = sub;
        this.email = email;
        this.name = name;
    }

}
