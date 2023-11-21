package org.guzzing.studayserver.domain.dashboard.service.dto.response;

import java.util.List;

public record DashBoardFindByIdsResults(
        List<DashBoardFindByIdsResult> dashBoards
) {

    public record DashBoardFindByIdsResult(
            Long dashboardId,
            Long lessonId
    ) {

    }
}
