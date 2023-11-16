package org.guzzing.studayserver.domain.dashboard.controller.dto.response;

public record DashboardPatchResponse(
        long dashboardId,
        boolean isActive
) {

}
