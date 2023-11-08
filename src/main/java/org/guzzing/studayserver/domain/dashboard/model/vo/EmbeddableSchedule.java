package org.guzzing.studayserver.domain.dashboard.model.vo;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
//@Embeddable
public class EmbeddableSchedule {

    private DayOfWeek dayOfWeek;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public EmbeddableSchedule(
            final DayOfWeek dayOfWeek,
            final LocalDateTime startTime,
            final LocalDateTime endTime
    ) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
