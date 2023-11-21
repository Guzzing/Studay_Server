package org.guzzing.studayserver.domain.dashboard.service.access;

import java.time.LocalTime;
import java.util.List;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.domain.dashboard.service.DashboardService;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.DashboardScheduleAccessResult;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.LessonScheduleAccessResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResult;
import org.springframework.stereotype.Service;

@Service
public class DashboardAccessServiceImpl implements DashboardAccessService {

    private static final String SEPARATOR = ":";

    private final DashboardService dashboardService;

    public DashboardAccessServiceImpl(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Override
    public DashboardScheduleAccessResult getDashboardSchedule(Long dashboardId) {
        final DashboardGetResult dashboardInfo = dashboardService.findDashboardInfo(dashboardId);

        final List<LessonScheduleAccessResult> schedules = dashboardInfo.scheduleInfos().schedules()
                .stream()
                .map(scheduleInfo -> new LessonScheduleAccessResult(
                        scheduleInfo.dayOfWeek(),
                        getTime(scheduleInfo.startTime()),
                        getTime((scheduleInfo.endTime()))))
                .toList();

        return new DashboardScheduleAccessResult(
                dashboardInfo.childInfo().childNickName(),
                dashboardInfo.academyInfo().academyName(),
                dashboardInfo.lessonInfo().subject(),
                Periodicity.WEEKLY,
                schedules
        );
    }

    private LocalTime getTime(final String time) {
        String[] split = time.split(SEPARATOR);
        try {
            int hour = Integer.parseInt(split[0]);
            int minute = Integer.parseInt(split[1]);

            return LocalTime.of(hour, minute);

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("유효하지 않은 시간입니다.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("시간은 숫자여야 합니다.");
        }
    }

}
