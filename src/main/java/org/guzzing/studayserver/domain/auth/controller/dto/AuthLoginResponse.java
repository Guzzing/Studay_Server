package org.guzzing.studayserver.domain.auth.controller.dto;

import org.guzzing.studayserver.domain.auth.service.dto.AuthLoginResult;

public record AuthLoginResponse(
        String appToken,
        Boolean isNewMember,
        Long userId,
        String name
) {

    public static AuthLoginResponse from(AuthLoginResult authLoginResult) {
        return new AuthLoginResponse(
                authLoginResult.appToken(),
                authLoginResult.isNewMember(),
                authLoginResult.userId(),
                authLoginResult.name()
        );
    }
}

