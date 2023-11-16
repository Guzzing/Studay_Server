package org.guzzing.studayserver.domain.acdademycalendar.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "academy_time_templates")
public class AcademyTimeTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_week",
            nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_datetime_of_attendance",
            columnDefinition = "TIMESTAMP",
            nullable = false)
    private LocalDate startDateOfAttendance;

    @Column(name = "end_datetime_of_attendance",
            columnDefinition = "TIMESTAMP",
            nullable = false)
    private LocalDate endDateOfAttendance;

    @Column(name = "isAlarmed",
            nullable = false)
    private boolean isAlarmed;


    @Column(name="member_id",
            nullable = false)
    private Long memberId;

    @Column(name = "child_id",
            nullable = false)
    private Long childId;

    @Column(name = "dashboard_id",
            nullable = false)
    private Long dashboardId;

    @Column(name="memo",
            columnDefinition = "text")
    private String memo;


    protected AcademyTimeTemplate() {
    }

    protected AcademyTimeTemplate(
            DayOfWeek dayOfWeek,
            LocalDate startDateOfAttendance,
            LocalDate endDateOfAttendance,
            boolean isAlarmed,
            Long memberId,
            Long childId,
            Long dashboardId,
            String memo) {
        this.dayOfWeek = dayOfWeek;
        this.startDateOfAttendance = startDateOfAttendance;
        this.endDateOfAttendance = endDateOfAttendance;
        this.isAlarmed = isAlarmed;
        this.childId = childId;
        this.dashboardId = dashboardId;
        this.memberId = memberId;
        this.memo = memo;
    }

    public static AcademyTimeTemplate of(
            DayOfWeek dayOfWeek,
            LocalDate startDateOfAttendance,
            LocalDate endDateOfAttendance,
            boolean isAlarmed,
            Long memberId,
            Long childId,
            Long dashboardId,
            String memo) {
        return new AcademyTimeTemplate(
                dayOfWeek,
                startDateOfAttendance,
                endDateOfAttendance,
                isAlarmed,
                memberId,
                childId,
                dashboardId,
                memo

        );
    }




}
