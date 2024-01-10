package org.guzzing.studayserver.domain.dashboard.repository;

import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.springframework.data.jpa.repository.Query;

public interface DashboardRepository {

    Dashboard save(final Dashboard dashboard);

    Dashboard findDashboardById(final Long dashboardId);

    Optional<Dashboard> findById(final Long dashboardId);

    @Query("SELECT d FROM Dashboard d WHERE d.childId = :childId AND d.isActive = true AND d.isDeleted = false ")
    List<Dashboard> findActiveOnlyByChildId(final Long childId);

    @Query("SELECT d FROM Dashboard d WHERE d.childId = :childId AND d.isDeleted = false ")
    List<Dashboard> findAllByChildId(final Long childId);

    List<Dashboard> findAll();

    List<Dashboard> findByIds(List<Long> dashboardIds);

    void deleteByChildIds(List<Long> childIds);
}
