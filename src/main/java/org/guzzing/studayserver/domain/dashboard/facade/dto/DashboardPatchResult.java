package org.guzzing.studayserver.domain.dashboard.facade.dto;

public record DashboardPatchResult(
        long dashboardId,
        boolean isActive
) {

}
