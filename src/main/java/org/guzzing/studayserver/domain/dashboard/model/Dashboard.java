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
import org.guzzing.studayserver.global.exception.DashboardException;

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

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public Dashboard(
            final Long childId,
            final Long academyId,
            final Long lessonId,
            final List<DashboardSchedule> dashboardSchedules,
            final FeeInfo feeInfo,
            final List<SimpleMemoType> simpleMemoTypes,
            final boolean isActive,
            final boolean isDeleted
    ) {
        setScheduleWithDashboard(dashboardSchedules);

        this.childId = childId;
        this.academyId = academyId;
        this.lessonId = lessonId;
        this.dashboardSchedules = dashboardSchedules;
        this.feeInfo = feeInfo;
        this.simpleMemoTypes = simpleMemoTypes;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }

    public Dashboard toggleActive() {
        this.isActive = !isActive;
        return this;
    }

    public void delete() {
        if (this.isActive()) {
            throw new DashboardException("비활성화된 대시보드만 삭제가 가능합니다.");
        }

        this.isDeleted = true;
    }

    public Dashboard update(final Dashboard source) {
        this.childId = source.getChildId();
        this.academyId = source.getAcademyId();
        this.lessonId = source.getLessonId();
        this.dashboardSchedules = source.getDashboardSchedules();
        this.feeInfo = source.getFeeInfo();
        this.simpleMemoTypes = source.getSimpleMemoTypes();
        this.isActive = source.isActive();
        this.isDeleted = source.isDeleted();

        return this;
    }

    private void setScheduleWithDashboard(final List<DashboardSchedule> dashboardSchedules) {
        dashboardSchedules.forEach(dashboardSchedule -> dashboardSchedule.setDashboard(this));
    }

}
