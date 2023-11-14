package org.guzzing.studayserver.domain.dashboard.repository;

import java.util.List;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DashboardJpaRepository extends JpaRepository<Dashboard, Long>, DashboardRepository {

    @Query("select d from Dashboard d where d.childId = :childId and d.isActive = true")
    List<Dashboard> findActiveOnlyByChildId(final Long childId);

}
