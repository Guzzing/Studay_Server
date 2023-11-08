package org.guzzing.studayserver.domain.dashboard.model.vo.schedule;

import static lombok.AccessLevel.PROTECTED;
import static org.guzzing.studayserver.domain.dashboard.model.vo.schedule.Repeatance.BIWEEKLY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.schedule.Repeatance.WEEKLY;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.guzzing.studayserver.global.exception.DashboardException;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class Schedule {

    private DayOfWeek dayOfWeek;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    protected Schedule(
            final DayOfWeek dayOfWeek,
            final LocalDateTime startTime,
            final LocalDateTime endTime,
            final Repeatance repeatance
    ) {
        validateRepeatanceDayOfWeek(repeatance, dayOfWeek);
        validateTimeSuitability(startTime, endTime);

        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Schedule of(
            final DayOfWeek dayOfWeek,
            final LocalDateTime startTime,
            final LocalDateTime endTime,
            final Repeatance repeatance
    ) {
        return new Schedule(dayOfWeek, startTime, endTime, repeatance);
    }

    private void validateRepeatanceDayOfWeek(
            final Repeatance repeatance,
            final DayOfWeek dayOfWeek
    ) {
        if (isWeeklyRepeatance(repeatance) && dayOfWeek != null) {
            throw new DashboardException("요일 설정을 할 수 없는 반복 타입입니다.");
        }
    }

    private boolean isWeeklyRepeatance(Repeatance repeatance) {
        return repeatance == WEEKLY || repeatance == BIWEEKLY;
    }

    private void validateTimeSuitability(
            final LocalDateTime startTime,
            final LocalDateTime endTime
    ) {
        if (startTime.isAfter(endTime)) {
            throw new DashboardException("시작 시간이 종료 시간 보다 늦습니다.");
        }
    }

}
