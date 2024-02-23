package org.guzzing.studayserver.domain.academy.repository.academy;

import org.guzzing.studayserver.domain.academy.repository.dto.*;
import org.guzzing.studayserver.domain.academy.repository.dto.request.AcademyByLocationWithCursorRepositoryRequest;
import org.guzzing.studayserver.domain.academy.repository.dto.response.AcademiesByFilterWithScroll;
import org.guzzing.studayserver.domain.academy.repository.dto.response.AcademiesByLocationWithScrollRepositoryResponse;
import org.guzzing.studayserver.domain.academy.repository.dto.response.AcademyByLocationWithCursorNotLikeRepositoryResponse;
import org.guzzing.studayserver.domain.academy.repository.dto.response.AcademyByLocationWithCursorRepositoryResponse;

public interface AcademyQueryRepository {

    AcademyByLocationWithCursorRepositoryResponse findAcademiesByLocationByCursor(
        AcademyByLocationWithCursorRepositoryRequest request);

    AcademyByLocationWithCursorNotLikeRepositoryResponse findAcademiesByCursorAndNotLike(
        AcademyByLocationWithCursorRepositoryRequest request);

    AcademiesByLocationWithScrollRepositoryResponse findAcademiesByLocation(
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
