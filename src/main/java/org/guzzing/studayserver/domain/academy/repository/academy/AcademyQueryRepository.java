package org.guzzing.studayserver.domain.academy.repository.academy;

import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByFilterWithScroll;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByLocationWithScroll;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyFilterCondition;

public interface AcademyQueryRepository {

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
