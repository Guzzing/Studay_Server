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
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(QuerydslTestConfig.class)
class HolidayRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HolidayRepository holidayRepository;

    @BeforeEach
    void setUp() {
        Holiday newYear = new Holiday(LocalDate.of(2022, 1, 1), "New Year");
        Holiday lunarNewYear = new Holiday(LocalDate.of(2022, 1, 22), "Lunar New Year");
        entityManager.persist(newYear);
        entityManager.persist(lunarNewYear);
        entityManager.flush();
    }

    @DisplayName("해당 년,월에 공휴일을 반환한다.")
    @Test
    void whenFindByYearAndMonth_thenReturnHolidays() {
        // Given
        int year = 2022;
        int month = 1;

        // When
        List<Holiday> foundHolidays = holidayRepository.findByYearMonth(year, month);

        // Then
        assertThat(foundHolidays).hasSize(2);
        assertThat(foundHolidays).extracting(Holiday::getDateName).containsOnly("New Year", "Lunar New Year");
    }
}
