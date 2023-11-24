package org.guzzing.studayserver.domain.holiday.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.holiday.model.Holiday;
import org.guzzing.studayserver.domain.holiday.repository.HolidayRepository;
import org.guzzing.studayserver.domain.holiday.service.result.HolidayFindByYearMonthResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    @Transactional(readOnly = true)
    public HolidayFindByYearMonthResult findByYearMonth(int year, int month) {
        List<Holiday> holidays = holidayRepository.findByYearMonth(year, month);

        Map<LocalDate, List<String>> holidayMap = new HashMap<>();

        holidays.forEach(h ->
                holidayMap.computeIfAbsent(h.getDate(), k -> new ArrayList<>()).add(h.getDateName())
        );

        return HolidayFindByYearMonthResult.from(holidayMap);
    }
}
