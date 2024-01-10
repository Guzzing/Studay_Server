package org.guzzing.studayserver.domain.holiday.repository;

import java.util.List;
import org.guzzing.studayserver.domain.holiday.model.Holiday;

public interface HolidayRepository {

    List<Holiday> findByYearMonth(int year, int month);
}
