package org.guzzing.studayserver.domain.dashboard.repository;

import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardJpaRepository extends JpaRepository<Dashboard, Long> {

}
