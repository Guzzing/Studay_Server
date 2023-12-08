package org.guzzing.studayserver.domain.academy.repository.academy;

import org.guzzing.studayserver.domain.academy.repository.dto.*;


public interface AcademyQueryRepository {

    AcademiesByLocationWithScroll findAcademiesByLocation(
            String pointFormat,
            Long memberId,
            Long beforeLastId,
            int pageSize);

    AcademiesByFilterWithScroll filterAcademies(
            AcademyFilterCondition academyFilterCondition,
            Long memberId,
            int pageNumber,
            int pageSize);
}
