package org.guzzing.studayserver.domain.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.guzzing.studayserver.domain.child.model.NickName;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleUserResponse {

    private String sub;
    private String email;
    private NickName name;

    public GoogleUserResponse(String sub, String email, NickName name) {
        this.sub = sub;
        this.email = email;
        this.name = name;
    }

}
