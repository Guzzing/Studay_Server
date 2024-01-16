package org.guzzing.studayserver.domain.calendar.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Stream;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.AcademyCalendarCreateRequest;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.AcademyCalendarUpdateRequest;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.AttendanceDate;
import org.guzzing.studayserver.domain.calendar.controller.dto.request.LessonTime;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.testutil.fixture.academycalender.AcademyCalenderFixture;
import org.guzzing.studayserver.testutil.security.WithMockCustomOAuth2LoginUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Transactional
class AcademyCalendarControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName(" 예외가 발생하는 상황을 검증한다.")
    @Nested
    class ThrowException {

        @DisplayName("스케줄 생성할 때 요청값에 대해 검증한다.")
        @WithMockCustomOAuth2LoginUser
        @ParameterizedTest
        @MethodSource("provideInvalidCreateRequests")
        void createAcademyCalendar(AcademyCalendarCreateRequest academyCalendarCreateRequest) throws Exception {
            //Then
            mvc.perform(post("/academy-schedules")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(academyCalendarCreateRequest))
                            .with(csrf()))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("스케줄 수정할 때 요청값에 대해 검증한다.")
        @WithMockCustomOAuth2LoginUser
        @ParameterizedTest
        @MethodSource("provideInvalidUpdateRequests")
        void updateSchedule(AcademyCalendarUpdateRequest academyCalendarUpdateRequest) throws Exception {
            mvc.perform(put("/academy-schedules")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(academyCalendarUpdateRequest))
                            .with(csrf()))
                    .andExpect(status().isBadRequest());
        }

        private static Stream<AcademyCalendarCreateRequest> provideInvalidCreateRequests() {
            return Stream.of(
                    //잘못된 수업 정보
                    new AcademyCalendarCreateRequest(
                            List.of(),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),

                    //잘못된 시간 형식
                    new AcademyCalendarCreateRequest(
                            List.of(
                                    new AcademyCalendarCreateRequest.LessonScheduleCreateRequest(
                                            DayOfWeek.FRIDAY.toString(),
                                            new LessonTime("1300", "14:00")
                                    )
                            ),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),
                    new AcademyCalendarCreateRequest(
                            List.of(
                                    new AcademyCalendarCreateRequest.LessonScheduleCreateRequest(
                                            DayOfWeek.FRIDAY.toString(),
                                            new LessonTime("-13:00", "14:00")
                                    )
                            ),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),
                    new AcademyCalendarCreateRequest(
                            List.of(
                                    new AcademyCalendarCreateRequest.LessonScheduleCreateRequest(
                                            DayOfWeek.FRIDAY.toString(),
                                            new LessonTime("13:00", "50:00")
                                    )
                            ),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),

                    // 잘뭇된 날짜 데이터
                    new AcademyCalendarCreateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleCreateRequest()),
                            new AttendanceDate("2023-12-15",
                                    "2023-12-13"),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),
                    new AcademyCalendarCreateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleCreateRequest()),
                            new AttendanceDate("2023-12-15",
                                    "2030-12-15"),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),

                    //잘못된 날짜 형식
                    new AcademyCalendarCreateRequest(
                            List.of(),
                            new AttendanceDate("20231215",
                                    "2024-12-15"),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),
                    new AcademyCalendarCreateRequest(
                            List.of(),
                            new AttendanceDate("2023-13-15",
                                    "2024-12-15"),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),

                    //잘못된 Enum
                    new AcademyCalendarCreateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleCreateRequest()),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            "SEMI_MONTH",
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),
                    new AcademyCalendarCreateRequest(
                            List.of(
                                    new AcademyCalendarCreateRequest.LessonScheduleCreateRequest(
                                            "MMONDAY",
                                            new LessonTime("13:00", "14:00")
                                    )
                            ),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),

                    // null관련
                    new AcademyCalendarCreateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleCreateRequest()),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            null,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),
                    new AcademyCalendarCreateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleCreateRequest()),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            Periodicity.WEEKLY.toString(),
                            null,
                            1L,
                            "화요일마다 저녁 제공"
                    ),
                    new AcademyCalendarCreateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleCreateRequest()),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            null,
                            "화요일마다 저녁 제공"
                    )
            );
        }

        private static Stream<AcademyCalendarUpdateRequest> provideInvalidUpdateRequests() {
            return Stream.of(
                    //잘못된 수업 정보
                    new AcademyCalendarUpdateRequest(
                            List.of(),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true

                    ),

                    //잘못된 시간 형식
                    new AcademyCalendarUpdateRequest(
                            List.of(
                                    new AcademyCalendarUpdateRequest.LessonScheduleUpdateRequest(
                                            DayOfWeek.FRIDAY.toString(),
                                            new LessonTime("1300", "14:00")
                                    )
                            ),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),

                    new AcademyCalendarUpdateRequest(
                            List.of(
                                    new AcademyCalendarUpdateRequest.LessonScheduleUpdateRequest(
                                            DayOfWeek.FRIDAY.toString(),
                                            new LessonTime("13:00", "-14:00")
                                    )
                            ),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),
                    new AcademyCalendarUpdateRequest(
                            List.of(
                                    new AcademyCalendarUpdateRequest.LessonScheduleUpdateRequest(
                                            DayOfWeek.FRIDAY.toString(),
                                            new LessonTime("13:00", "14:1100")
                                    )
                            ),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),

                    // 잘뭇된 날짜 데이터
                    new AcademyCalendarUpdateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleUpdateRequest()),
                            new AttendanceDate("2023-12-15",
                                    "2023-12-13"),
                            true,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),
                    new AcademyCalendarUpdateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleUpdateRequest()),
                            new AttendanceDate("2023-12-15",
                                    "2050-12-15"),
                            true,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),
                    // 잘뭇된 날짜 형식
                    new AcademyCalendarUpdateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleUpdateRequest()),
                            new AttendanceDate("20231215",
                                    "2024-12-13"),
                            true,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),
                    new AcademyCalendarUpdateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleUpdateRequest()),
                            new AttendanceDate("2023-13-15",
                                    "2024-12-15"),
                            true,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),

                    //잘못된 Enum
                    new AcademyCalendarUpdateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleUpdateRequest()),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            "SEMI_MONTH",
                            true
                    ),
                    new AcademyCalendarUpdateRequest(
                            List.of(
                                    new AcademyCalendarUpdateRequest.LessonScheduleUpdateRequest(
                                            "MMONDAY",
                                            new LessonTime("13:00", "14:00")
                                    )
                            ),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),

                    // null관련
                    new AcademyCalendarUpdateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleUpdateRequest()),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            null,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),
                    new AcademyCalendarUpdateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleUpdateRequest()),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            null,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),
                    new AcademyCalendarUpdateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleUpdateRequest()),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            1L,
                            null,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),
                    new AcademyCalendarUpdateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleUpdateRequest()),
                            new AttendanceDate("2023-12-15",
                                    "2024-12-15"),
                            true,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            null
                    )
            );
        }

    }

}
