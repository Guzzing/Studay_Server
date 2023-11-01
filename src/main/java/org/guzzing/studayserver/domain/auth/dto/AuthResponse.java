package org.guzzing.studayserver.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthResponse {

    private String appToken;
    private Boolean isNewMember;
    private Long userId;
    private String name;

    @Builder
    public AuthResponse(String appToken, Boolean isNewMember, Long userId, String name) {
        this.appToken = appToken;
        this.isNewMember = isNewMember;
        this.userId = userId;
        this.name = name;
    }

}
