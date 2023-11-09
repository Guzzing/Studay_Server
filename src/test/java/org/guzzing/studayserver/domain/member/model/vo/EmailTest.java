package org.guzzing.studayserver.domain.member.model.vo;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @DisplayName("정상적인 이메일 형식이라면 객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "test@example.com",
            "firstname.lastname@example.com",
            "email@subdomain.example.com",
            "username+tag@example.com",
            "email@example.co.jp",
            "email@example.name"
    })
    void create_success(String validEmail) {
        // Then
        assertThatCode(() -> new Email(validEmail))
                .doesNotThrowAnyException();
    }

    @DisplayName("잘못된 이메일 형식이라면 예외를 발생시킨다.")

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "       ",
            "plainaddress",
            "@missingusername.com",
            "username@.com",
            "username@.com.com",
            "username@example..com",
            "username@-example.com",
            "username@example#.com"
    })
    void create_failure_dueToInvalidFormat(String invalidEmail) {
        // Then
        assertThatThrownBy(() -> new Email(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class);
    }

}





