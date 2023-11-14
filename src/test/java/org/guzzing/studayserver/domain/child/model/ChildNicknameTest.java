package org.guzzing.studayserver.domain.child.model;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.guzzing.studayserver.domain.member.model.NickName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class ChildNicknameTest {

    @DisplayName("정상 값이라면 객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "TestNickName",
            "AnotherValidName",
            "YetAnotherValidName"
    })
    void create_success(String nickname) {
        // Then
        assertThatCode(() -> new ChildNickname(nickname))
                .doesNotThrowAnyException();
    }

    @DisplayName("잘못된 값이라면 예외를 발생시킨다.")
    @ParameterizedTest
    @MethodSource("provideInvalidNicknames")
    void create_failure_throwException(String invalidNickname) {
        // Then
        assertThatThrownBy(() -> new ChildNickname(invalidNickname))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<String> provideInvalidNicknames() {
        return Stream.of(
                null,
                "   ",
                "A".repeat(NickName.NAME_MAX_LENGTH + 1)
        );
    }
}
