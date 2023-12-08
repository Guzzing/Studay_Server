package org.guzzing.studayserver.domain.calendar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Entity
@Table(name = "academy_schedules")
public class AcademySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academy_time_template_id", nullable = false)
    private AcademyTimeTemplate academyTimeTemplate;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Column(name = "lesson_start_time", nullable = false)
    private LocalTime lessonStartTime;

    @Column(name = "lesson_end_time", nullable = false)
    private LocalTime lessonEndTime;

    protected AcademySchedule() {
    }

    protected AcademySchedule(
            AcademyTimeTemplate academyTimeTemplate,
            LocalDate scheduleDate,
            LocalTime lessonStartTime,
            LocalTime lessonEndTime
    ) {
        this.academyTimeTemplate = academyTimeTemplate;
        this.scheduleDate = scheduleDate;
        this.lessonStartTime = lessonStartTime;
        this.lessonEndTime = lessonEndTime;
    }

    public static AcademySchedule of(
            AcademyTimeTemplate academyTimeTemplate,
            LocalDate scheduleDate,
            LocalTime lessonStartTime,
            LocalTime lessonEndTime
    ) {
        return new AcademySchedule(
                academyTimeTemplate,
                scheduleDate,
                lessonStartTime,
                lessonEndTime
        );
    }

    public Long getDashboardId() {
        return academyTimeTemplate.getDashboardId();
    }

}
