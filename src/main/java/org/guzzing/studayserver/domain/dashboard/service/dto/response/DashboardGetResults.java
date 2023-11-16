package org.guzzing.studayserver.domain.dashboard.service.dto.response;

import java.util.List;

public record DashboardGetResults(
        List<DashboardGetResult> results
) {

}
