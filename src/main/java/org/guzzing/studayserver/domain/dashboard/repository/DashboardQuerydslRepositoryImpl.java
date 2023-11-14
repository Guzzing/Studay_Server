package org.guzzing.studayserver.domain.dashboard.repository;

import static org.guzzing.studayserver.domain.dashboard.model.QDashboard.dashboard;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardQuerydslRepositoryImpl implements
        DashboardQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public DashboardQuerydslRepositoryImpl(final JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Dashboard> findActiveOnlyByChildId(Long childId) {
        return jpaQueryFactory.selectFrom(dashboard)
                .where(dashboard.childId.eq(childId)
                        .and(dashboard.isActive.isTrue()))
                .fetch();
    }
}
