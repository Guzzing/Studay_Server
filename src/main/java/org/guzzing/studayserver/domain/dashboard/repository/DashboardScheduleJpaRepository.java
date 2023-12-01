package org.guzzing.studayserver.domain.dashboard.repository;

import java.util.List;
import org.guzzing.studayserver.domain.dashboard.model.DashboardSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DashboardScheduleJpaRepository extends JpaRepository<DashboardSchedule, Long> {

    @Modifying(clearAutomatically = true)
    @Query("""
        delete from DashboardSchedule ds
        where ds.dashboard.childId in :childIds
    """)
    void deleteByChildIds(final List<Long> childIds);

}
