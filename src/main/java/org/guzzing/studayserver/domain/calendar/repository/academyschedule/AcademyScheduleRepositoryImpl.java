package org.guzzing.studayserver.domain.calendar.repository.academyschedule;

import java.time.LocalDate;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.model.AcademySchedule;
import org.guzzing.studayserver.domain.calendar.model.AcademyTimeTemplate;
import org.guzzing.studayserver.domain.calendar.repository.dto.AcademyCalenderDetailInfo;
import org.springframework.stereotype.Repository;

@Repository
public class AcademyScheduleRepositoryImpl implements AcademyScheduleRepository{

    private final AcademyScheduleJpaRepository academyScheduleJpaRepository;

    public AcademyScheduleRepositoryImpl(AcademyScheduleJpaRepository academyScheduleJpaRepository) {
        this.academyScheduleJpaRepository = academyScheduleJpaRepository;
    }

    @Override
    public AcademySchedule save(AcademySchedule academySchedule) {
        return academyScheduleJpaRepository.save(academySchedule);
    }

    @Override
    public List<AcademySchedule> findAll() {
        return academyScheduleJpaRepository.findAll();
    }

    @Override
    public AcademyTimeTemplate findDistinctAcademyTimeTemplate(Long academyScheduleId) {
        return academyScheduleJpaRepository.findDistinctAcademyTimeTemplate(academyScheduleId);
    }

    @Override
    public void deleteAllByAcademyTimeTemplateId(Long academyTimeTemplateId) {
        academyScheduleJpaRepository.deleteAllByAcademyTimeTemplateId(academyTimeTemplateId);
    }

    @Override
    public void deleteAfterUpdatedStartDate(Long academyTimeTemplateId, LocalDate startDateOfAttendanceToUpdate) {
        academyScheduleJpaRepository.deleteAfterUpdatedStartDate(academyTimeTemplateId, startDateOfAttendanceToUpdate);
    }

    @Override
    public List<AcademySchedule> findByAcademyTimeTemplateId(Long academyTimeTemplateId) {
        return academyScheduleJpaRepository.findByAcademyTimeTemplateId(academyTimeTemplateId);
    }

    @Override
    public void deleteById(Long academyScheduleId) {
        academyScheduleJpaRepository.deleteById(academyScheduleId);
    }

    @Override
    public AcademyCalenderDetailInfo findTimeTemplateByChildIdAndScheduleId(Long scheduleId, Long childId) {
        return academyScheduleJpaRepository.findTimeTemplateByChildIdAndScheduleId(scheduleId, childId);
    }

    @Override
    public List<AcademySchedule> findByYearMonth(List<Long> childIds, int year, int month) {
        return academyScheduleJpaRepository.findByYearMonth(childIds, year, month);
    }

    @Override
    public List<AcademySchedule> findByDate(List<Long> childIds, LocalDate date) {
        return academyScheduleJpaRepository.findByDate(childIds, date);
    }

    @Override
    public Long findDashboardIdByAcademyScheduleId(Long academyScheduleId) {
        return academyScheduleJpaRepository.findDashboardIdByAcademyScheduleId(academyScheduleId);
    }

    @Override
    public LocalDate findScheduleDate(Long scheduleId) {
        return academyScheduleJpaRepository.findScheduleDate(scheduleId);
    }

    @Override
    public List<AcademySchedule> findByDate(LocalDate startDateOfAttendance, LocalDate endDateOfAttendance,
            Long childId) {
        return academyScheduleJpaRepository.findByDate(startDateOfAttendance, endDateOfAttendance, childId);
    }
}
