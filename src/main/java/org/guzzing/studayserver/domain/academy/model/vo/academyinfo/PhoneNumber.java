package org.guzzing.studayserver.domain.academy.model.vo.academyinfo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.Getter;

@Getter
@Embeddable
public class PhoneNumber {

    @Transient
    private static final String REGEX = "^\\d{2,3}-\\d{3,4}-\\d{3,4}$";

    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;

    public PhoneNumber(final String phoneNumber) {
        validate(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    protected PhoneNumber() {
    }

    private void validate(final String phoneNumber) {
        if (!phoneNumber.isBlank() && (!Pattern.matches(REGEX, phoneNumber))) {
            throw new IllegalArgumentException("올바른 전화번호 형식이 아닙니다.");

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(REGEX, that.REGEX) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(REGEX, phoneNumber);
    }

}
