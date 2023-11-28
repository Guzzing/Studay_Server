package org.guzzing.studayserver.domain.dashboard.service.access;

import java.time.LocalTime;
import java.util.List;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.calendar.model.Periodicity;
import org.guzzing.studayserver.domain.child.service.ChildAccessService;
import org.guzzing.studayserver.domain.dashboard.service.DashboardService;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.DashboardScheduleAccessResult;
import org.guzzing.studayserver.domain.dashboard.service.access.dto.LessonScheduleAccessResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.springframework.stereotype.Service;

@Service
public class DashboardAccessServiceImpl implements DashboardAccessService {

    private static final String SEPARATOR = ":";

    private final DashboardService dashboardService;
    private final ChildAccessService childAccessService;
    private final AcademyAccessService academyAccessService;

    public DashboardAccessServiceImpl(
            final DashboardService dashboardService,
            final ChildAccessService childAccessService,
            final AcademyAccessService academyAccessService
    ) {
        this.dashboardService = dashboardService;
        this.childAccessService = childAccessService;
        this.academyAccessService = academyAccessService;
    }

    @Override
    public DashboardScheduleAccessResult getDashboardSchedule(Long dashboardId) {
        final DashboardResult result = dashboardService.findDashboard(dashboardId);

        final List<LessonScheduleAccessResult> schedules = result.scheduleInfos()
                .schedules()
                .stream()
                .map(scheduleInfo -> new LessonScheduleAccessResult(
                        scheduleInfo.dayOfWeek(),
                        getTime(scheduleInfo.startTime()),
                        getTime((scheduleInfo.endTime()))))
                .toList();

        return new DashboardScheduleAccessResult(
                getChildNickName(result.childId()),
                getAcademyName(result.academyId()),
                getLessonSubject(result.lessonId()),
                Periodicity.WEEKLY,
                schedules);
    }

    private String getChildNickName(final long childId) {
        return childAccessService.findChildInfo(childId)
                .childNickName();
    }

    private String getAcademyName(final long academyId) {
        return academyAccessService.findAcademyInfo(academyId)
                .academyName();
    }

    private String getLessonSubject(final long lessonId) {
        return academyAccessService.findLessonInfo(lessonId)
                .curriculum();
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
