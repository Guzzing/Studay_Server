package org.guzzing.studayserver.domain.dashboard.repository;

import java.util.List;

public interface DashboardScheduleRepository {

    void deleteByChildIds(final List<Long> childIds);
}
