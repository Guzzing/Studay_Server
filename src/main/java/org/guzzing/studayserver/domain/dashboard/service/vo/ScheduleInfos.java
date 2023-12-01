package org.guzzing.studayserver.domain.dashboard.service.vo;

import java.util.List;

public record ScheduleInfos(
        List<ScheduleInfo> schedules
) {

    public ScheduleInfos {
        validate(schedules);
    }

    private void validate(final List<ScheduleInfo> schedules) {
        schedules.forEach(scheduleInfo -> this.validateDuplicatedSchedules(scheduleInfo, schedules));
    }

    private void validateDuplicatedSchedules(final ScheduleInfo schedule, final List<ScheduleInfo> schedules) {
        long count = schedules.stream()
                .filter(scheduleInfo -> schedule.dayOfWeek() == scheduleInfo.dayOfWeek())
                .count();

        if (count > 1) {
            throw new IllegalStateException("날짜가 중복됩니다.");
        }
    }

}
