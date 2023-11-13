package org.guzzing.studayserver.domain.dashboard.controller.converter;

import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.GOOD_FACILITY;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.GOOD_MANAGEMENT;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.KINDNESS;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.LOVELY_TEACHING;
import static org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType.SHUTTLE_AVAILABILITY;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.guzzing.studayserver.domain.dashboard.controller.dto.request.DashboardPostRequest;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardGetResponse;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardGetResponses;
import org.guzzing.studayserver.domain.dashboard.controller.dto.response.DashboardPostResponse;
import org.guzzing.studayserver.domain.dashboard.controller.vo.Schedule;
import org.guzzing.studayserver.domain.dashboard.controller.vo.SimpleMemo;
import org.guzzing.studayserver.domain.dashboard.model.vo.DayOfWeek;
import org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResults;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardPostResult;
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
                convertToSimpleMemoTypeMap(request.simpleMemo()));
    }

    public DashboardPostResponse from(final DashboardPostResult result) {
        return new DashboardPostResponse(
                result.dashboardId(),
                result.childId(),
                result.academyId(),
                result.lessonId());
    }

    public DashboardGetResponse from(final DashboardGetResult result) {
        return new DashboardGetResponse(
                result.dashboardId(),
                result.childInfo(),
                result.academyInfo(),
                result.lessonInfo(),
                convertToSchedules(result.scheduleInfos()),
                result.paymentInfo(),
                convertToSimpleMemo(result.simpleMemoTypeMap()),
                result.active(),
                result.deleted());
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
                        scheduleInfo.dayOfWeek().name(),
                        scheduleInfo.startTime(),
                        scheduleInfo.endTime(),
                        scheduleInfo.repeatance().name()))
                .toList();
    }

    private SimpleMemo convertToSimpleMemo(final Map<SimpleMemoType, Boolean> simpleMemoMap) {
        return new SimpleMemo(
                simpleMemoMap.get(KINDNESS),
                simpleMemoMap.get(GOOD_FACILITY),
                simpleMemoMap.get(CHEAP_FEE),
                simpleMemoMap.get(GOOD_MANAGEMENT),
                simpleMemoMap.get(LOVELY_TEACHING),
                simpleMemoMap.get(SHUTTLE_AVAILABILITY));
    }

    private ScheduleInfos convertToScheduleInfos(final List<Schedule> schedules) {
        List<ScheduleInfo> infos = schedules.stream()
                .map(schedule -> new ScheduleInfo(
                        DayOfWeek.of(schedule.dayOfWeek()),
                        schedule.startTime(), schedule.endTime(),
                        Repeatance.of(schedule.repeatance())))
                .toList();

        return new ScheduleInfos(infos);
    }

    private Map<SimpleMemoType, Boolean> convertToSimpleMemoTypeMap(final SimpleMemo simpleMemo) {
        Map<SimpleMemoType, Boolean> map = new ConcurrentHashMap<>();

        map.put(KINDNESS, simpleMemo.kindness());
        map.put(GOOD_FACILITY, simpleMemo.goodFacility());
        map.put(CHEAP_FEE, simpleMemo.cheapFee());
        map.put(GOOD_MANAGEMENT, simpleMemo.goodManagement());
        map.put(LOVELY_TEACHING, simpleMemo.lovelyTeaching());
        map.put(SHUTTLE_AVAILABILITY, simpleMemo.shuttleAvailability());

        return map;
    }

}
