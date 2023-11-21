package org.guzzing.studayserver.domain.dashboard.service.access;

import org.guzzing.studayserver.domain.dashboard.service.access.dto.DashboardScheduleAccessResult;

public interface DashboardAccessService {

    DashboardScheduleAccessResult getDashboardSchedule(Long dashboardId);
}
