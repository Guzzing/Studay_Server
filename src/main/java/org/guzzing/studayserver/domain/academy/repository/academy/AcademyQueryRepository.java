package org.guzzing.studayserver.domain.academy.repository.academy;

import org.guzzing.studayserver.domain.academy.repository.dto.*;

public interface AcademyQueryRepository {

    AcademyByLocationWithCursorRepositoryResponse findAcademiesByLocationByCursor(
        AcademyByLocationWithCursorRepositoryRequest request);

    AcademiesByLocationWithScroll findAcademiesByLocation(
        String pointFormat,
        Long memberId,
        int pageNumber,
        int pageSize);

    AcademiesByFilterWithScroll filterAcademies(
        AcademyFilterCondition academyFilterCondition,
        Long memberId,
        int pageNumber,
        int pageSize);
}
