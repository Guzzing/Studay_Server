package org.guzzing.studayserver.domain.dashboard;

import org.springframework.stereotype.Service;

@Service
public class DashboardAccessServiceImpl implements DashboardAccessService{

    /** 세영님한테 받는 정보 확인의 용도
     * 응답: 아이 ID, 학원 이름, 수업이름, 요일, 시간, 반복주기
     * 요청: dashboardID
     */
    @Override
    public DashboardScheduleAccessResult getDashboardSchedule(Long dashboardId) {
        return null;
    }
}
