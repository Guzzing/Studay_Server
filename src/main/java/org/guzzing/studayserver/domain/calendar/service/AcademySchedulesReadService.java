package org.guzzing.studayserver.domain.calendar.service;

import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.calendar.repository.academyschedule.AcademyScheduleRepository;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyScheduleFindByDateResults;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyScheduleFindByDateResults.AcademyScheduleFindByDateResult;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyScheduleYearMonthResults;
import org.guzzing.studayserver.domain.calendar.service.dto.result.AcademyScheduleYearMonthResults.AcademyScheduleYearMonthResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcademySchedulesReadService {

    private final AcademyScheduleRepository academyScheduleRepository;

    public AcademySchedulesReadService(AcademyScheduleRepository academyScheduleRepository) {
        this.academyScheduleRepository = academyScheduleRepository;
    }

    @Transactional(readOnly = true)
    public AcademyScheduleYearMonthResults getScheduleByYearMonth(List<Long> childIds, int year, int month) {
        List<AcademySchedule> academySchedules = academyScheduleRepository.findByYearMonth(childIds, year, month);

        List<AcademyScheduleYearMonthResult> academyScheduleYearMonthResults = academySchedules.stream()
                .map(a -> new AcademyScheduleYearMonthResult(
                        a.getAcademyTimeTemplate().getId(),
                        a.getAcademyTimeTemplate().getChildId(),
                        a.getId(),
                        a.getScheduleDate()
                ))
                .toList();

        return new AcademyScheduleYearMonthResults(academyScheduleYearMonthResults);
    }

    @Transactional(readOnly = true)
    public AcademyScheduleFindByDateResults findByDate(List<Long> childIds, LocalDate date) {
        List<AcademySchedule> academySchedules = academyScheduleRepository.findByDate(childIds, date);

        List<AcademyScheduleFindByDateResult> academyScheduleResults = academySchedules.stream()
                .map(ash -> new AcademyScheduleFindByDateResult(
                        ash.getId(),
                        ash.getLessonStartTime(),
                        ash.getLessonEndTime(),
                        ash.getAcademyTimeTemplate().getDashboardId(),
                        ash.getAcademyTimeTemplate().getChildId()
                )).toList();

        return new AcademyScheduleFindByDateResults(academyScheduleResults);
    }
}
