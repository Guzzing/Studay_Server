package org.guzzing.studayserver.domain.holiday.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.holiday.model.Holiday;
import org.guzzing.studayserver.testutil.QuerydslTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
class HolidayRepositoryTest {

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private HolidayJpaRepository holidayJpaRepository;

    @DisplayName("해당 년,월에 공휴일을 반환한다.")
    @Test
    void whenFindByYearAndMonth_thenReturnHolidays() {
        // Given
        Holiday newYear = new Holiday(LocalDate.of(2022, 1, 1), "New Year");
        Holiday lunarNewYear = new Holiday(LocalDate.of(2022, 1, 22), "Lunar New Year");
        holidayJpaRepository.save(newYear);
        holidayJpaRepository.save(lunarNewYear);

        int year = 2022;
        int month = 1;

        // When
        List<Holiday> foundHolidays = holidayRepository.findByYearMonth(year, month);

        // Then
        assertThat(foundHolidays).hasSize(2);
        assertThat(foundHolidays).extracting(Holiday::getDateName).containsOnly("New Year", "Lunar New Year");
    }
}
