package org.guzzing.studayserver.domain.dashboard.repository;

import static org.guzzing.studayserver.domain.dashboard.model.QDashboard.dashboard;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public DashboardQuerydslRepository(final JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Dashboard> findActiveOnlyByChildId(Long childId) {
        return jpaQueryFactory.selectFrom(dashboard)
                .where(dashboard.childId.eq(childId)
                        .and(dashboard.isActive.isTrue()))
                .fetch();
    }
}
