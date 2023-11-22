package org.guzzing.studayserver.domain.dashboard.service.vo;

import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.NONE;
import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.WEEKLY;

import java.time.DayOfWeek;
import org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance;
import org.guzzing.studayserver.global.exception.DashboardException;

public record ScheduleInfo(
        DayOfWeek dayOfWeek,
        String startTime,
        String endTime,
        Repeatance repeatance
) {

    public ScheduleInfo {
        validateRepeatanceDayOfWeek(repeatance, dayOfWeek);
    }

    private void validateRepeatanceDayOfWeek(
            final Repeatance repeatance,
            final DayOfWeek dayOfWeek
    ) {
        if (dayOfWeek != null && !isWeeklyRepeatance(repeatance)) {
            throw new DashboardException("요일 설정을 할 수 없는 반복 타입입니다.");
        }
    }

    private boolean isWeeklyRepeatance(Repeatance repeatance) {
        return repeatance == WEEKLY || repeatance == NONE;
    }

}
