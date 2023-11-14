package org.guzzing.studayserver.domain.dashboard.repository;

import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.global.exception.DashboardException;

public interface DashboardRepository {

    Dashboard save(final Dashboard dashboard);

    default Dashboard findDashboardById(final Long dashboardId) {
        return this.findById(dashboardId)
                .orElseThrow(() -> new DashboardException("존재하지 않는 대시보드입니다."));
    }

    Optional<Dashboard> findById(final Long dashboardId);

    List<Dashboard> findActiveOnlyByChildId(final Long childId);

    List<Dashboard> findAllByChildId(final Long childId);

    List<Dashboard> findAll();

}
