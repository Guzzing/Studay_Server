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
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.guzzing.studayserver.domain.dashboard.model.vo.EmbeddableSchedules;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;

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
    private EmbeddableSchedules embeddableSchedules;

    @Enumerated(value = EnumType.STRING)
    private Repeatance repeatance;

    @Embedded
    private FeeInfo feeInfo;

    @Column(name = "payment_day", nullable = false)
    private LocalDate paymentDay;

    @Column(name = "simple_memos", nullable = false)
    private List<SimpleMemoType> simpleMemoTypes;

    protected Dashboard(
            final Long childId,
            final Long lessonId,
            final EmbeddableSchedules embeddableSchedules,
            final Repeatance repeatance,
            final FeeInfo feeInfo,
            final LocalDate paymentDay,
            final List<SimpleMemoType> simpleMemoTypes
    ) {
        this.childId = childId;
        this.lessonId = lessonId;
        this.embeddableSchedules = embeddableSchedules;
        this.repeatance = repeatance;
        this.feeInfo = feeInfo;
        this.paymentDay = paymentDay;
        this.simpleMemoTypes = simpleMemoTypes;
    }

    public static Dashboard of(
            final Long childId,
            final Long lessonId,
            final EmbeddableSchedules embeddableSchedules,
            final Repeatance repeatance,
            final FeeInfo feeInfo,
            final LocalDate paymentDay,
            final List<SimpleMemoType> simpleMemoTypes
    ) {
        return new Dashboard(childId, lessonId, embeddableSchedules, repeatance, feeInfo, paymentDay, simpleMemoTypes);
    }

}
