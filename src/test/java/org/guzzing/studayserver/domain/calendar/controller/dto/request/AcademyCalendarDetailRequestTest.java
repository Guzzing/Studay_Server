package org.guzzing.studayserver.domain.calendar.controller.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AcademyCalendarDetailRequestTest {

    @ParameterizedTest
    @MethodSource("outOfRangeDateProvider")
    @DisplayName("날짜가 범위를 벗어나는 경우 예외를 던진다.")
    void construct_outOfRangeDate_throwException(String requestedDate) {
        assertThatThrownBy(
                () -> new AcademyCalendarDetailRequest(
                        requestedDate,
                        1L,
                        new ArrayList<>()
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("날짜가 들어오지 않는 경우 예외를 던진다.")
    void construct_invalidDateFormat_throwException(String requestedDate) {
        assertThatThrownBy(
                () -> new AcademyCalendarDetailRequest(
                        requestedDate,
                        1L,
                        new ArrayList<>()
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("아이디가 음수인 경우 예외를 던진다.")
    void construct_invalidLessonId_throwException() {
        assertThatThrownBy(
                () -> new AcademyCalendarDetailRequest(
                        "2022-12-01",
                        -1L,
                        List.of(
                                new AcademyCalendarDetailRequest.ChildrenScheduleDetailRequest(
                                        1L,
                                        1L
                                )
                        ))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("아이 정보가 들어오지 않는 경우 예외를 던진다.")
    void construct_invalidChildrenInfo_throwException(
            List<AcademyCalendarDetailRequest.ChildrenScheduleDetailRequest> childrenInfos) {
        assertThatThrownBy(
                () -> new AcademyCalendarDetailRequest(
                        "2022-12-01",
                        1L,
                        childrenInfos
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<String> outOfRangeDateProvider() {
        return Stream.of(
                "2022-23-13",
                "22222222222",
                "100000-12-14",
                "2023-11-50"
        );
    }

}
