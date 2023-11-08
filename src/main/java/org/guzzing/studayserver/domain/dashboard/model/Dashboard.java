package org.guzzing.studayserver.domain.dashboard.model;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
import org.guzzing.studayserver.domain.dashboard.model.vo.schedule.DashboardSchedules;
import org.guzzing.studayserver.domain.dashboard.model.vo.schedule.Repeatance;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "dashboards")
public class Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "child_id", nullable = false)
    private Long childId;

    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

    @Column(name = "schedules", nullable = false)
    private DashboardSchedules schedules;

    @Enumerated(value = EnumType.STRING)
    private Repeatance repeatance;

    @Embedded
    private FeeInfo feeInfo;

    @Column(name = "simple_memos", nullable = false)
    private List<SimpleMemoType> simpleMemoTypes;

    protected Dashboard(
            final Long childId,
            final Long lessonId,
            final DashboardSchedules schedules,
            final Repeatance repeatance,
            final FeeInfo feeInfo,
            final List<SimpleMemoType> simpleMemoTypes
    ) {
        this.childId = childId;
        this.lessonId = lessonId;
        this.schedules = schedules;
        this.repeatance = repeatance;
        this.feeInfo = feeInfo;
        this.simpleMemoTypes = simpleMemoTypes;
    }

}
