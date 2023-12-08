package org.guzzing.studayserver.domain.academy.repository.academy;

import java.util.List;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByFilterWithScroll;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByLocation;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByLocationWithScroll;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyByFiltering;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyFilterCondition;

public interface AcademyQueryRepository {

    List<AcademiesByLocation> findAcademiesByLocation(String pointFormat, Long memberId);

    List<AcademyByFiltering> filterAcademies(AcademyFilterCondition academyFilterCondition, Long memberId);

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
