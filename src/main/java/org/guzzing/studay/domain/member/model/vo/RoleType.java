package org.guzzing.studay.domain.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {

    USER("ROLE_USER", "일반 사용자 권한"),
    NONE("NONE", "권한 없음");

    private final String code;
    private final String name;

    public static RoleType of(String code) {
        return Arrays.stream(RoleType.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(NONE);
    }

}
