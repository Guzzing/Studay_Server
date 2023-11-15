package org.guzzing.studayserver.domain.auth.controller.dto;

import org.guzzing.studayserver.domain.auth.service.dto.AuthLogoutResult;

public record AuthLogoutResponse(
        boolean isLogout
) {

    public static AuthLogoutResponse from(AuthLogoutResult authLogoutResult) {
        return new AuthLogoutResponse(
                authLogoutResult.isLogout()
        );
    }
}
