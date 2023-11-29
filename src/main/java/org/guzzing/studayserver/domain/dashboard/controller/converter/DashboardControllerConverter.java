package org.guzzing.studayserver.domain.dashboard.controller.converter;

import static org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance.WEEKLY;

import java.time.DayOfWeek;
import java.util.List;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPostRequest;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPutRequest;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardGetResponse;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardGetResponses;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardPatchResponse;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardPostResponse;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardPutResponse;
import org.guzzing.studayserver.domain.dashboard.controller.vo.Schedule;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardGetResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardGetResults;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPatchResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPostResult;
import org.guzzing.studayserver.domain.dashboard.facade.dto.DashboardPutResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPutParam;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;
import org.springframework.stereotype.Component;

@Component
public class DashboardControllerConverter {

    public DashboardPostParam to(final DashboardPostRequest request) {
        return new DashboardPostParam(
                request.childId(),
                request.academyId(),
                request.lessonId(),
                convertToScheduleInfos(request.schedules()),
                request.paymentInfo(),
                request.simpleMemo());
    }

    public DashboardPutParam to(final long dashboardId, final DashboardPutRequest request) {
        return new DashboardPutParam(
                dashboardId,
                request.paymentInfo(),
                request.simpleMemo());
    }

    public DashboardPostResponse from(final DashboardPostResult result) {
        return new DashboardPostResponse(
                result.dashboardId(),
                result.childId(),
                result.academyId(),
                result.lessonId());
    }

    public DashboardPutResponse from(final DashboardPutResult result) {
        return new DashboardPutResponse(
                result.dashboardId(),
                result.paymentInfo(),
                result.simpleMemo());
    }

    public DashboardPatchResponse from(final DashboardPatchResult result) {
        return new DashboardPatchResponse(result.dashboardId(), result.isActive());
    }

    public DashboardGetResponse from(final DashboardGetResult result) {
        return new DashboardGetResponse(
                result.dashboardId(),
                result.childInfo(),
                result.academyInfo(),
                result.lessonInfo(),
                convertToSchedules(result.scheduleInfos()),
                result.paymentInfo(),
                result.simpleMemo(),
                result.isActive(),
                result.isDeleted());
    }

    public DashboardGetResponses from(final DashboardGetResults getResults) {
        final List<DashboardGetResponse> responses = getResults.results()
                .stream()
                .map(this::from)
                .toList();

        return new DashboardGetResponses(responses);
    }

    private List<Schedule> convertToSchedules(final ScheduleInfos scheduleInfos) {
        return scheduleInfos.schedules().stream()
                .map(scheduleInfo -> new Schedule(
                        scheduleInfo.dayOfWeek().getValue(),
                        scheduleInfo.startTime(),
                        scheduleInfo.endTime()
                ))
                .toList();
    }

    private ScheduleInfos convertToScheduleInfos(final List<Schedule> schedules) {
        List<ScheduleInfo> infos = schedules.stream()
                .map(schedule -> new ScheduleInfo(
                        DayOfWeek.of(schedule.dayOfWeek()),
                        schedule.startTime(), schedule.endTime(),
                        WEEKLY
                ))
                .toList();

        return new ScheduleInfos(infos);
    }

}
