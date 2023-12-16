package org.guzzing.studayserver.domain.dashboard.service;

import java.util.List;

import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.domain.dashboard.repository.DashboardRepository;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashBoardFindByIdsResults;
import org.guzzing.studayserver.domain.dashboard.service.dto.response.DashBoardFindByIdsResults.DashBoardFindByIdsResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardReadService {

    private final DashboardRepository dashboardRepository;

    public DashboardReadService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @Transactional(readOnly = true)
    public DashBoardFindByIdsResults findByIds(List<Long> dashboardIds) {
        List<Dashboard> dashboards = dashboardRepository.findByIds(dashboardIds);

        List<DashBoardFindByIdsResult> dashBoardFindByIdsResults = dashboards.stream()
                .map(d -> new DashBoardFindByIdsResult(
                        d.getId(),
                        d.getLessonId()
                )).toList();

        return new DashBoardFindByIdsResults(dashBoardFindByIdsResults);
    }

}
