package org.guzzing.studayserver.domain.dashboard.repository;

import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.global.exception.DashboardException;
import org.springframework.data.jpa.repository.Query;

public interface DashboardRepository {

    Dashboard save(final Dashboard dashboard);

    default Dashboard findDashboardById(final Long dashboardId) {
        return this.findById(dashboardId)
                .orElseThrow(() -> new DashboardException("존재하지 않는 대시보드입니다."));
    }

    Optional<Dashboard> findById(final Long dashboardId);

    @Query("SELECT d FROM Dashboard d WHERE d.childId = :childId AND d.isActive = true AND d.isDeleted = false ")
    List<Dashboard> findActiveOnlyByChildId(final Long childId);

    @Query("SELECT d FROM Dashboard d WHERE d.childId = :childId AND d.isDeleted = false ")
    List<Dashboard> findAllByChildId(final Long childId);

    List<Dashboard> findAll();

}
