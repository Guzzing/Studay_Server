package org.guzzing.studayserver.domain.holiday.repository;

import java.util.List;
import org.guzzing.studayserver.domain.holiday.model.Holiday;
import org.springframework.stereotype.Repository;

@Repository
public class HolidayRepositoryImpl implements HolidayRepository {

    private final HolidayJpaRepository holidayJpaRepository;

    public HolidayRepositoryImpl(HolidayJpaRepository holidayJpaRepository) {
        this.holidayJpaRepository = holidayJpaRepository;
    }

    @Override
    public List<Holiday> findByYearMonth(int year, int month) {
        return holidayJpaRepository.findByYearMonth(year, month);
    }
}
