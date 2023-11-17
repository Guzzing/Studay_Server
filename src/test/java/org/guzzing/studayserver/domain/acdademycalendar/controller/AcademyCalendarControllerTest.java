package org.guzzing.studayserver.domain.acdademycalendar.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request.AcademyCalendarCreateRequest;
import org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request.AcademyCalendarUpdateRequest;
import org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request.LessonScheduleCreateRequest;
import org.guzzing.studayserver.domain.acdademycalendar.controller.dto.request.LessonScheduleUpdateRequest;
import org.guzzing.studayserver.domain.acdademycalendar.model.Periodicity;
import org.guzzing.studayserver.domain.acdademycalendar.service.AcademyCalendarService;
import org.guzzing.studayserver.testutil.WithMockCustomOAuth2LoginUser;
import org.guzzing.studayserver.testutil.fixture.academycalender.AcademyCalenderFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AcademyCalendarController.class)
class AcademyCalendarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AcademyCalendarService academyCalendarService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName(" 예외가 발생하는 상황을 검증한다.")
    @Nested
    class throwException {

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
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2024, 12, 15),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),

                    // 잘뭇된 날짜 데이터
                    new AcademyCalendarCreateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleCreateRequest()),
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2023, 12, 13),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),
                    new AcademyCalendarCreateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleCreateRequest()),
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2030, 12, 13),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),

                    //잘못된 Enum
                    new AcademyCalendarCreateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleCreateRequest()),
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2023, 12, 18),
                            true,
                            "SEMI_MONTH",
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),
                    new AcademyCalendarCreateRequest(
                            List.of(
                                    new LessonScheduleCreateRequest(
                                            "MMONDAY",
                                            LocalTime.of(13, 0),
                                            LocalTime.of(14, 0)
                                    )
                            ),
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2024, 12, 15),
                            true,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),

                    // null관련
                    new AcademyCalendarCreateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleCreateRequest()),
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2024, 12, 15),
                            null,
                            Periodicity.WEEKLY.toString(),
                            1L,
                            1L,
                            "화요일마다 저녁 제공"
                    ),
                    new AcademyCalendarCreateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleCreateRequest()),
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2024, 12, 15),
                            true,
                            Periodicity.WEEKLY.toString(),
                            null,
                            1L,
                            "화요일마다 저녁 제공"
                    ),
                    new AcademyCalendarCreateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleCreateRequest()),
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2024, 12, 15),
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
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2024, 12, 15),
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
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2023, 12, 13),
                            true,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),
                    new AcademyCalendarUpdateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleUpdateRequest()),
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2030, 12, 13),
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
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2023, 12, 18),
                            true,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            "SEMI_MONTH",
                            true
                    ),
                    new AcademyCalendarUpdateRequest(
                            List.of(
                                    new LessonScheduleUpdateRequest(
                                            "MMONDAY",
                                            LocalTime.of(13, 0),
                                            LocalTime.of(14, 0)
                                    )
                            ),
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2024, 12, 15),
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
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2024, 12, 15),
                            null,
                            1L,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),
                    new AcademyCalendarUpdateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleUpdateRequest()),
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2024, 12, 15),
                            true,
                            null,
                            1L,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),
                    new AcademyCalendarUpdateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleUpdateRequest()),
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2024, 12, 15),
                            true,
                            1L,
                            null,
                            "화요일마다 저녁 제공",
                            Periodicity.WEEKLY.toString(),
                            true
                    ),
                    new AcademyCalendarUpdateRequest(
                            List.of(AcademyCalenderFixture.mondayLessonScheduleUpdateRequest()),
                            LocalDate.of(2023, 12, 15),
                            LocalDate.of(2023, 12, 18),
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
