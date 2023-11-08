package org.guzzing.studayserver.domain.dashboard.service.vo;

import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.BIWEEKLY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.WEEKLY;

import java.time.LocalDateTime;
import org.guzzing.studayserver.domain.dashboard.model.vo.DayOfWeek;
import org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance;
import org.guzzing.studayserver.global.exception.DashboardException;

public record ScheduleInfo(
        DayOfWeek dayOfWeek,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Repeatance repeatance
) {

    public ScheduleInfo {
        validateRepeatanceDayOfWeek(repeatance, dayOfWeek);
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

}
