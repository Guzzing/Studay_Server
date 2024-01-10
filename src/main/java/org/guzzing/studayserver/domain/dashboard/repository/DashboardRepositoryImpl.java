package org.guzzing.studayserver.domain.dashboard.repository;

import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.dashboard.model.Dashboard;
import org.guzzing.studayserver.global.exception.DashboardException;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardRepositoryImpl implements DashboardRepository{

    private final DashboardJpaRepository dashboardJpaRepository;
    private final DashboardQuerydslRepository dashboardQuerydslRepository;

    public DashboardRepositoryImpl(DashboardJpaRepository dashboardJpaRepository,
            DashboardQuerydslRepository dashboardQuerydslRepository) {
        this.dashboardJpaRepository = dashboardJpaRepository;
        this.dashboardQuerydslRepository = dashboardQuerydslRepository;
    }

    @Override
    public Dashboard save(Dashboard dashboard) {
        return dashboardJpaRepository.save(dashboard);
    }

    @Override
    public Dashboard findDashboardById(Long dashboardId) {
        return dashboardJpaRepository.findById(dashboardId)
                .orElseThrow(() -> new DashboardException("존재하지 않는 대시보드입니다."));
    }

    @Override
    public Optional<Dashboard> findById(Long dashboardId) {
        return dashboardJpaRepository.findById(dashboardId);
    }

    @Override
    public List<Dashboard> findActiveOnlyByChildId(Long childId) {
        return dashboardQuerydslRepository.findActiveOnlyByChildId(childId);
    }

    @Override
    public List<Dashboard> findAllByChildId(Long childId) {
        return dashboardJpaRepository.findAllByChildId(childId);
    }

    @Override
    public List<Dashboard> findAll() {
        return dashboardJpaRepository.findAll();
    }

    @Override
    public List<Dashboard> findByIds(List<Long> dashboardIds) {
        return dashboardJpaRepository.findByIds(dashboardIds);
    }

    @Override
    public void deleteByChildIds(List<Long> childIds) {
        dashboardJpaRepository.deleteByChildIds(childIds);
    }
}
