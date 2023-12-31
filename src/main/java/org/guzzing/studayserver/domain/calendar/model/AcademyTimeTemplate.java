package org.guzzing.studayserver.domain.calendar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.DayOfWeek;
import java.time.LocalDate;
import lombok.Getter;

@Getter
@Entity
@Table(name = "academy_time_templates")
public class AcademyTimeTemplate {

    private static final int MAX_DIFFERENCE_YEAR = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_week",
            nullable = false)
    @Enumerated(value = EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_datetime_of_attendance",
            columnDefinition = "TIMESTAMP",
            nullable = false)
    private LocalDate startDateOfAttendance;

    @Column(name = "end_datetime_of_attendance",
            columnDefinition = "TIMESTAMP",
            nullable = false)
    private LocalDate endDateOfAttendance;

    @Column(name = "is_alarmed",
            nullable = false)
    private boolean isAlarmed;

    @Column(name = "child_id",
            nullable = false)
    private Long childId;

    @Column(name = "dashboard_id",
            nullable = false)
    private Long dashboardId;

    @Column(name = "memo",
            columnDefinition = "text")
    private String memo;


    protected AcademyTimeTemplate() {
    }

    protected AcademyTimeTemplate(
            DayOfWeek dayOfWeek,
            LocalDate startDateOfAttendance,
            LocalDate endDateOfAttendance,
            boolean isAlarmed,
            Long childId,
            Long dashboardId,
            String memo) {
        this.dayOfWeek = dayOfWeek;
        this.startDateOfAttendance = startDateOfAttendance;
        this.endDateOfAttendance = endDateOfAttendance;
        this.isAlarmed = isAlarmed;
        this.childId = childId;
        this.dashboardId = dashboardId;
        this.memo = memo;
    }

    public static AcademyTimeTemplate of(
            DayOfWeek dayOfWeek,
            LocalDate startDateOfAttendance,
            LocalDate endDateOfAttendance,
            boolean isAlarmed,
            Long childId,
            Long dashboardId,
            String memo) {
        return new AcademyTimeTemplate(
                dayOfWeek,
                startDateOfAttendance,
                endDateOfAttendance,
                isAlarmed,
                childId,
                dashboardId,
                memo

        );
    }

    public void changeEndDateOfAttendance(LocalDate endDateOfAttendance) {
        if (isValidPeriod(endDateOfAttendance)) {
            this.endDateOfAttendance = endDateOfAttendance;
        }
    }

    private boolean isValidPeriod(LocalDate endDate) {
        if (endDate.isAfter(startDateOfAttendance.plusYears(MAX_DIFFERENCE_YEAR))) {
            throw new IllegalArgumentException(String.format("스케줄 일정은 %d년을 넘을 수 없습니다.", MAX_DIFFERENCE_YEAR));
        }

        if (endDate.isBefore(startDateOfAttendance)) {
            throw new IllegalArgumentException("마지막 등원일이 시작 등원일보다 이전일 수는 없습니다.");
        }

        return true;
    }

}
