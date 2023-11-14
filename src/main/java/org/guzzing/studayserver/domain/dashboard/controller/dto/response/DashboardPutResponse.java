package org.guzzing.studayserver.domain.dashboard.controller.dto.response;

public record DashboardPutResponse(
        long dashboardId,
        long childId,
        long academyId,
        long lessonId
) {

}
