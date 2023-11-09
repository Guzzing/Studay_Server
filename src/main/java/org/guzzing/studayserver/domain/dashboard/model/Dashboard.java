package org.guzzing.studayserver.domain.dashboard.model;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "dashboards")
public class Dashboard {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "child_id", nullable = false)
    private Long childId;

    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

    @OneToMany(
            mappedBy = "dashboard", fetch = LAZY,
            cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<DashboardSchedule> dashboardSchedules;

    @Embedded
    private FeeInfo feeInfo;

    @Column(name = "simple_memos", nullable = false)
    private List<SimpleMemoType> simpleMemoTypes;

    public Dashboard(
            final Long childId,
            final Long lessonId,
            final List<DashboardSchedule> dashboardSchedules,
            final FeeInfo feeInfo,
            final List<SimpleMemoType> simpleMemoTypes
    ) {
        setScheduleWithDashboard(dashboardSchedules);

        this.childId = childId;
        this.lessonId = lessonId;
        this.dashboardSchedules = dashboardSchedules;
        this.feeInfo = feeInfo;
        this.simpleMemoTypes = simpleMemoTypes;
    }

    private void setScheduleWithDashboard(final List<DashboardSchedule> dashboardSchedules) {
        dashboardSchedules.forEach(dashboardSchedule -> dashboardSchedule.setDashboard(this));
    }

}
