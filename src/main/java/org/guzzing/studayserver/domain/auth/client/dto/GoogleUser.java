package org.guzzing.studayserver.domain.auth.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.guzzing.studayserver.domain.child.model.NickName;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleUser(
        String sub,
        String email,
        NickName name
) {
}
