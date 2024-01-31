package org.guzzing.studayserver.domain.calendar_info.controller.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.service.dto.result.CalendarFindSchedulesByDateResults;
import org.guzzing.studayserver.domain.calendar.service.dto.result.CalendarFindSchedulesByDateResults.CalendarFindSchedulesByDateResult;
import org.guzzing.studayserver.domain.calendar_info.controller.response.CalendarFindSchedulesByDateResponses.CalendarFindSchedulesByDateResponse;
import org.guzzing.studayserver.domain.calendar_info.controller.response.CalendarFindSchedulesByDateResponses.SameStartTimeScheduleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalendarFindSchedulesByDateResponsesTest {

    @DisplayName("유효한 응답으로 변환되어야 한다.")
    @Test
    void shouldConvertedToValidResponse() {
        // Given
        LocalDate testDate = LocalDate.of(2023, 1, 1);
        CalendarFindSchedulesByDateResult schedule1 = new CalendarFindSchedulesByDateResult(
                1L,
                "https://team09-resources-bucket.s3.ap-northeast-1.amazonaws.com/childImage1.png",
                101L,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                201L,
                "Academy A",
                "Math");
        CalendarFindSchedulesByDateResult schedule2 = new CalendarFindSchedulesByDateResult(
                2L,
                "https://team09-resources-bucket.s3.ap-northeast-1.amazonaws.com/childImage2.png",
                102L,
                LocalTime.of(9, 0),
                LocalTime.of(10, 30),
                202L,
                "Academy A",
                "Science");
        CalendarFindSchedulesByDateResults results = new CalendarFindSchedulesByDateResults(
                List.of(schedule1, schedule2));

        // When
        CalendarFindSchedulesByDateResponses response = CalendarFindSchedulesByDateResponses.from(testDate, results);

        // 결과 검증
        assertThat(response.date()).isEqualTo(testDate);

        assertThat(response.dateResponses()).hasSize(1);
        CalendarFindSchedulesByDateResponse actualSameStartTime = response.dateResponses().get(0);

        assertThat(actualSameStartTime.schedules()).hasSize(2);
        List<SameStartTimeScheduleResponse> schedules = actualSameStartTime.schedules();
        assertThat(schedules.get(0).lessonId()).isEqualTo(201L);
        assertThat(schedules.get(1).lessonId()).isEqualTo(202L);
    }
}
