package org.guzzing.studayserver.domain.auth.service.dto;

public record AuthRefreshResult(
        String appToken,
        Long userId,
        String name
) {
    public static AuthRefreshResult of(String appToken, Long userId, String name) {
        return new AuthRefreshResult(appToken, userId, name);
    }
}
