package org.guzzing.studayserver.domain.auth.controller.dto;

import org.guzzing.studayserver.domain.auth.service.dto.AuthRefreshResult;

public record AuthRefreshResponse(
        String appToken,
        Long userId
) {

    public static AuthRefreshResponse from(AuthRefreshResult authRefreshResult) {
        return new AuthRefreshResponse(
                authRefreshResult.appToken(),
                authRefreshResult.userId()
        );
    }
}
