package org.guzzing.studayserver.domain.dashboard.repository;

import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;

public interface DashboardRepository {

    Dashboard save(final Dashboard dashboard);

    Optional<Dashboard> findById(final Long dashboardId);

    List<Dashboard> findActiveOnlyByChildId(final Long childId);

    List<Dashboard> findAllByChildId(final Long childId);

    List<Dashboard> findAll();

}
