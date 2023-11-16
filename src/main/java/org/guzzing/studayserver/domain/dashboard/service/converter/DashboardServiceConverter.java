package org.guzzing.studayserver.domain.dashboard.service.converter;

import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.model.DashboardSchedule;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardGetResults;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardPatchResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardPostResult;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardPutResult;
import org.guzzing.studayserver.domain.dashboard.service.vo.AcademyInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ChildInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.LessonInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;
import org.springframework.stereotype.Component;

@Component
public class DashboardServiceConverter {

    public Dashboard to(final DashboardPostParam param) {
        return new Dashboard(
                param.childId(),
                param.academyId(),
                param.lessonId(),
                convertToDashboardSchedules(param.scheduleInfos()),
                convertToFeeInfo(param.paymentInfo()),
                convertToSelectedSimpleMemoTypes(param.simpleMemoTypeMap()),
                true, false);
    }

    public DashboardPostResult postResultFrom(final Dashboard entity) {
        return new DashboardPostResult(
                entity.getId(),
                entity.getChildId(),
                entity.getAcademyId(),
                entity.getLessonId(),
                convertToScheduleInfos(entity.getDashboardSchedules()),
                convertToPaymentInfo(entity.getFeeInfo()),
                convertToSimpleMemoTypeMap(entity.getSimpleMemoTypes()),
                entity.isActive(),
                entity.isDeleted());
    }

    public DashboardPutResult putResultFrom(final Dashboard entity) {
        return new DashboardPutResult(
                entity.getId(),
                convertToPaymentInfo(entity.getFeeInfo()),
                convertToSimpleMemoTypeMap(entity.getSimpleMemoTypes()));
    }

    public DashboardPatchResult patchResultFrom(final Dashboard entity) {
        return new DashboardPatchResult(entity.getId(), entity.isActive());
    }

    public DashboardGetResult postResultFrom(
            final Dashboard entity,
            final ChildInfo childInfo,
            final AcademyInfo academyInfo,
            final LessonInfo lessonInfo
    ) {
        return new DashboardGetResult(
                entity.getId(),
                childInfo,
                academyInfo,
                lessonInfo,
                convertToScheduleInfos(entity.getDashboardSchedules()),
                convertToPaymentInfo(entity.getFeeInfo()),
                convertToSimpleMemoTypeMap(entity.getSimpleMemoTypes()),
                entity.isActive(),
                entity.isDeleted());
    }

    public DashboardGetResults postResultFrom(final List<DashboardGetResult> results) {
        return new DashboardGetResults(results);
    }

    public FeeInfo convertToFeeInfo(final PaymentInfo paymentInfo) {
        return new FeeInfo(
                paymentInfo.educationFee(),
                paymentInfo.bookFee(),
                paymentInfo.shuttleFee(),
                paymentInfo.etcFee(),
                paymentInfo.paymentDay());
    }

    public List<SimpleMemoType> convertToSelectedSimpleMemoTypes(final Map<SimpleMemoType, Boolean> map) {
        return SimpleMemoType.getSelectedSimpleMemos(map);
    }

    private List<DashboardSchedule> convertToDashboardSchedules(final ScheduleInfos scheduleInfos) {
        return scheduleInfos.schedules()
                .stream()
                .map(scheduleInfo -> new DashboardSchedule(
                        scheduleInfo.dayOfWeek(),
                        scheduleInfo.startTime(),
                        scheduleInfo.endTime(),
                        scheduleInfo.repeatance()
                ))
                .toList();
    }

    private ScheduleInfos convertToScheduleInfos(
            final List<DashboardSchedule> dashboardSchedules
    ) {
        final List<ScheduleInfo> scheduleInfos = dashboardSchedules.stream()
                .map(dashboardSchedule -> new ScheduleInfo(
                        dashboardSchedule.getDayOfWeek(),
                        dashboardSchedule.getStartTime(),
                        dashboardSchedule.getEndTime(),
                        dashboardSchedule.getRepeatance()))
                .toList();

        return new ScheduleInfos(scheduleInfos);
    }

    private PaymentInfo convertToPaymentInfo(final FeeInfo feeInfo) {
        return new PaymentInfo(
                feeInfo.getEducationFee(),
                feeInfo.getBookFee(),
                feeInfo.getShuttleFee(),
                feeInfo.getEtcFee(),
                feeInfo.getPaymentDay());
    }

    private Map<SimpleMemoType, Boolean> convertToSimpleMemoTypeMap(final List<SimpleMemoType> simpleMemoTypes) {
        return SimpleMemoType.convertToSimpleMemoMap(simpleMemoTypes);
    }

}
