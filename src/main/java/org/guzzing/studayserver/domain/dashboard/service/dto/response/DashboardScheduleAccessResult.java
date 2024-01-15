package org.guzzing.studayserver.domain.dashboard.service.dto.response;

public record DashboardScheduleAccessResult(
        Long childId,
        Long academyId,
        Long lessonId
) {

}
