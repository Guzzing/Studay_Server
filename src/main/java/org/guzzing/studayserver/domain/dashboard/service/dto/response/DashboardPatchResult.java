package org.guzzing.studayserver.domain.dashboard.service.dto.response;

public record DashboardPatchResult(
        long dashboardId,
        boolean active
) {

}
