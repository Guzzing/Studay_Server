package org.guzzing.studayserver.domain.dashboard.service.overlap;

import java.util.List;
import org.guzzing.studayserver.domain.dashboard.model.DashboardSchedule;
import org.guzzing.studayserver.global.time.TimeConverter;
import org.springframework.stereotype.Component;

@Component
public class DashboardScheduleOverlapChecker {

    public void checkScheduleOverlap(
            List<DashboardSchedule> dashboardSchedules,
            List<DashboardSchedule> childDashboardSchedules
    ) {
        final long overlapCount = childDashboardSchedules.stream()
                .filter(childDashboardSchedule -> dashboardSchedules.stream()
                        .anyMatch(dashboardSchedule -> isScheduleOverlap(dashboardSchedule, childDashboardSchedule)))
                .count();

        if (overlapCount > 0) {
            throw new IllegalStateException("시간이 겹치는 대시보드 스케줄이 있습니다.");
        }
    }

    private boolean isScheduleOverlap(DashboardSchedule dashboardSchedule, DashboardSchedule childDashboardSchedule) {
        final boolean isSameDayOfWeek = dashboardSchedule.getDayOfWeek() == childDashboardSchedule.getDayOfWeek();
        final boolean isAfterSchedule = TimeConverter.getLocalTime(dashboardSchedule.getStartTime()).isAfter(
                TimeConverter.getLocalTime(childDashboardSchedule.getEndTime()));
        final boolean isBeforeSchedules = TimeConverter.getLocalTime(dashboardSchedule.getEndTime()).isBefore(
                TimeConverter.getLocalTime(childDashboardSchedule.getStartTime()));
        final boolean isSameStartTime = TimeConverter.getLocalTime(dashboardSchedule.getStartTime()).equals(
                TimeConverter.getLocalTime(childDashboardSchedule.getStartTime()));
        final boolean isSameEndTime = TimeConverter.getLocalTime(dashboardSchedule.getEndTime()).equals(
                TimeConverter.getLocalTime(childDashboardSchedule.getEndTime()));

        return isSameDayOfWeek && (isAfterSchedule || isBeforeSchedules || isSameStartTime || isSameEndTime);
    }

}
