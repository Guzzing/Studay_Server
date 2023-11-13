package org.guzzing.studayserver.domain.dashboard.model;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.guzzing.studayserver.domain.dashboard.model.vo.DayOfWeek;
import org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "dashboard_schedules")
public class DashboardSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dashboard_id", nullable = false)
    private Dashboard dashboard;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time", nullable = false)
    private String endTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "repeatance")
    private Repeatance repeatance;

    public DashboardSchedule(
            final DayOfWeek dayOfWeek,
            final String startTime,
            final String endTime,
            final Repeatance repeatance
    ) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeatance = repeatance;
    }

    protected void setDashboard(final Dashboard dashboard) {
        this.dashboard = dashboard;
    }

}
