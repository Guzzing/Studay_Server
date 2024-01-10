package org.guzzing.studayserver.domain.dashboard.repository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardScheduleRepositoryImpl implements DashboardScheduleRepository {

    private final DashboardScheduleJpaRepository dashboardScheduleJpaRepository;

    public DashboardScheduleRepositoryImpl(DashboardScheduleJpaRepository dashboardScheduleJpaRepository) {
        this.dashboardScheduleJpaRepository = dashboardScheduleJpaRepository;
    }

    @Override
    public void deleteByChildIds(List<Long> childIds) {
        dashboardScheduleJpaRepository.deleteByChildIds(childIds);
    }
}
