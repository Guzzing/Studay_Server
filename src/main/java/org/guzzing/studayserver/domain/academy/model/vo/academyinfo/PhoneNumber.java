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
    private final String REGEX = "^\\d{2,3}-\\d{3,4}-\\d{3,4}$";

    @Column(name = "contact", nullable = true)
    private String contact;

    public PhoneNumber(final String contact) {
        validate(contact);
        this.contact = contact;
    }

    protected PhoneNumber() {
    }

    private void validate(final String contact) {
        if (!contact.isBlank()) {
            if (!Pattern.matches(REGEX, contact)) {
                throw new IllegalArgumentException("올바른 전화번호 형식이 아닙니다.");
            }
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
        return Objects.equals(REGEX, that.REGEX) && Objects.equals(contact, that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(REGEX, contact);
    }

}
