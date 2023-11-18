package org.guzzing.studayserver.domain.dashboard.repository;

import java.util.List;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;

public interface DashboardQuerydslRepository {

    List<Dashboard> findActiveOnlyByChildId(final Long childId);

}
