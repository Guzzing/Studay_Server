package org.guzzing.studayserver.domain.holiday.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.guzzing.studayserver.domain.holiday.model.Holiday;
import org.guzzing.studayserver.domain.holiday.repository.HolidayRepository;
import org.guzzing.studayserver.domain.holiday.service.result.HolidayFindByYearMonthResult;
import org.guzzing.studayserver.domain.holiday.service.result.HolidayFindByYearMonthResult.HolidayResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class HolidayServiceTest {

    @Mock
    private HolidayRepository holidayRepository;

    private HolidayService holidayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        holidayService = new HolidayService(holidayRepository);
    }

    @Test
    void whenFindByYearMonth_thenReturnHolidayMap() {
        // Given
        int year = 2022;
        int month = 1;
        List<Holiday> mockHolidays = Arrays.asList(
                new Holiday(LocalDate.of(year, month, 1), "New Year"),
                new Holiday(LocalDate.of(year, month, 22), "Lunar New Year")
        );
        given(holidayRepository.findByYearMonth(year, month)).willReturn(mockHolidays);

        // When
        HolidayFindByYearMonthResult result = holidayService.findByYearMonth(year, month);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.holidayResults()).hasSize(2);
        assertThat(result.holidayResults()).contains(
                new HolidayResult(LocalDate.of(year, month, 1), List.of("New Year")),
                new HolidayResult(LocalDate.of(year, month, 22), List.of("Lunar New Year")));
    }
}
