package org.guzzing.studayserver.domain.holiday.repository;

import java.util.List;
import org.guzzing.studayserver.domain.holiday.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HolidayJpaRepository extends JpaRepository<Holiday, Long> {

    @Query("SELECT h FROM Holiday h WHERE YEAR(h.date) = :year AND MONTH(h.date) = :month")
    List<Holiday> findByYearMonth(int year, int month);
}
