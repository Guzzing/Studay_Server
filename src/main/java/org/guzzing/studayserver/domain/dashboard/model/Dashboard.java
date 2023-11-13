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

    @Column(name = "academy_id", nullable = false)
    private Long academyId;

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

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    public Dashboard(
            final Long childId,
            final Long academyId,
            final Long lessonId,
            final List<DashboardSchedule> dashboardSchedules,
            final FeeInfo feeInfo,
            final List<SimpleMemoType> simpleMemoTypes,
            final boolean active,
            final boolean deleted
    ) {
        setScheduleWithDashboard(dashboardSchedules);

        this.childId = childId;
        this.academyId = academyId;
        this.lessonId = lessonId;
        this.dashboardSchedules = dashboardSchedules;
        this.feeInfo = feeInfo;
        this.simpleMemoTypes = simpleMemoTypes;
        this.active = active;
        this.deleted = deleted;
    }

    public Dashboard toggleActive() {
        this.active = !active;
        return this;
    }

    public void delete() {
        this.deleted = true;
    }

    private void setScheduleWithDashboard(final List<DashboardSchedule> dashboardSchedules) {
        dashboardSchedules.forEach(dashboardSchedule -> dashboardSchedule.setDashboard(this));
    }

}
