package org.guzzing.studayserver.domain.dashboard.repository;

import java.util.List;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DashboardJpaRepository extends
        JpaRepository<Dashboard, Long>, DashboardRepository, DashboardQuerydslRepository {

}
