package org.guzzing.studayserver.domain.dashboard.repository;

import java.util.List;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DashboardJpaRepository extends
        JpaRepository<Dashboard, Long>, DashboardRepository, DashboardQuerydslRepository {

    @Query("SELECT dab FROM Dashboard AS dab "
            + "JOIN FETCH dab.dashboardSchedules dabschs "
            + "WHERE dab.id IN :dashboardIds")
    List<Dashboard> findByIds(List<Long> dashboardIds);

    @Modifying(clearAutomatically = true)
    @Query("""
        delete from Dashboard d
        where d.childId in :childIds
    """)
    void deleteByChildIds(List<Long> childIds);
}
