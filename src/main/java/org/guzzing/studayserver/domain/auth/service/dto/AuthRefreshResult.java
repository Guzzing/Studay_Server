package org.guzzing.studayserver.domain.auth.service.dto;

public record AuthRefreshResult(
        String appToken,
        Long userId
) {
    public static AuthRefreshResult of(String appToken, Long userId) {
        return new AuthRefreshResult(appToken, userId);
    }
}
