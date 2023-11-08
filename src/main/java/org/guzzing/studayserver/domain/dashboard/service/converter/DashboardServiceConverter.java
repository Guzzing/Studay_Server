package org.guzzing.studayserver.domain.dashboard.service.converter;

import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.model.dto.PaymentInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.EmbeddableSchedule;
import org.guzzing.studayserver.domain.dashboard.model.vo.EmbeddableSchedules;
import org.guzzing.studayserver.domain.dashboard.model.vo.FeeInfo;
import org.guzzing.studayserver.domain.dashboard.model.vo.Repeatance;
import org.guzzing.studayserver.domain.dashboard.model.vo.SimpleMemoType;
import org.guzzing.studayserver.domain.dashboard.service.dto.request.DashboardPostParam;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashboardResult;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfo;
import org.guzzing.studayserver.domain.dashboard.service.vo.ScheduleInfos;
import org.springframework.stereotype.Component;

@Component
public class DashboardServiceConverter {

    public Dashboard to(final DashboardPostParam param) {
        return Dashboard.of(
                param.childId(),
                param.lessonId(),
                convertToEmbeddedSchedules(param.scheduleInfos()),
                param.repeatance(),
                convertToFeeInfo(param.paymentInfo()),
                param.paymentDay(),
                convertToSelectedSimpleMemoTypes(param.simpleMemoTypeMap())
        );
    }

    public DashboardResult from(final Long academyId, final Dashboard dashboard) {
        Repeatance repeatance = dashboard.getRepeatance();

        return new DashboardResult(
                dashboard.getId(),
                academyId,
                convertToScheduleInfos(dashboard.getEmbeddableSchedules(), repeatance),
                repeatance,
                dashboard.getChildId(),
                dashboard.getLessonId(),
                convertToPaymentInfo(dashboard.getFeeInfo()),
                dashboard.getPaymentDay(),
                convertToSimpleMemoTypeMap(dashboard.getSimpleMemoTypes())
        );
    }

    private EmbeddableSchedules convertToEmbeddedSchedules(final ScheduleInfos scheduleInfos) {
        List<EmbeddableSchedule> schedules = scheduleInfos.schedules().stream()
                .map(scheduleInfo -> new EmbeddableSchedule(
                        scheduleInfo.dayOfWeek(),
                        scheduleInfo.startTime(),
                        scheduleInfo.endTime()
                ))
                .toList();

        return new EmbeddableSchedules(schedules);
    }

    private FeeInfo convertToFeeInfo(final PaymentInfo paymentInfo) {
        return FeeInfo.of(
                paymentInfo.educationFee(),
                paymentInfo.bookFee(),
                paymentInfo.shuttleFee(),
                paymentInfo.etcFee());
    }

    private List<SimpleMemoType> convertToSelectedSimpleMemoTypes(final Map<SimpleMemoType, Boolean> map) {
        return SimpleMemoType.getSelectedSimpleMemos(map);
    }

    private ScheduleInfos convertToScheduleInfos(final EmbeddableSchedules embeddableSchedules,
            final Repeatance repeatance) {
        List<ScheduleInfo> scheduleInfos = embeddableSchedules.getSchedules().stream()
                .map(embeddableSchedule -> new ScheduleInfo(
                        embeddableSchedule.getDayOfWeek(),
                        embeddableSchedule.getStartTime(),
                        embeddableSchedule.getEndTime(),
                        repeatance))
                .toList();

        return new ScheduleInfos(scheduleInfos);
    }

    private PaymentInfo convertToPaymentInfo(final FeeInfo feeInfo) {
        return new PaymentInfo(
                feeInfo.getEducationFee(),
                feeInfo.getBookFee(),
                feeInfo.getShuttleFee(),
                feeInfo.getEtcFee());
    }

    private Map<SimpleMemoType, Boolean> convertToSimpleMemoTypeMap(final List<SimpleMemoType> simpleMemoTypes) {
        return SimpleMemoType.convertToSimpleMemoMap(simpleMemoTypes);
    }

}
