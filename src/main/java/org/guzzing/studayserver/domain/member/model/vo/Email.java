package org.guzzing.studayserver.domain.member.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    private static final String REGEX = "^[a-zA-Z0-9._%+-]+@([a-zA-Z0-9]+\\.)+[a-zA-Z]{2,}$";

    @Column(name = "email")
    private String value;

    public Email(String value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("이메일은 빈 값일 수 없습니다.");
        }
        if (!value.matches(REGEX)) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
