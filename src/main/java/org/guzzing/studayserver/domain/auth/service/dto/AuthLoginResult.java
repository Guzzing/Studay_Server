package org.guzzing.studayserver.domain.auth.service.dto;

public record AuthLoginResult(
        String appToken,
        Boolean isNewMember,
        Long userId,
        String name
) {

    public static AuthLoginResult of(String appToken, boolean isNewMember, Long userId, String name) {
        return new AuthLoginResult(appToken, isNewMember, userId, name);
    }
}
