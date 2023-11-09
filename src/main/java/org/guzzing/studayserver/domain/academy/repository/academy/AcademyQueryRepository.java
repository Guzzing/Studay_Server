package org.guzzing.studayserver.domain.academy.repository.academy;

import java.util.List;

import org.guzzing.studayserver.domain.academy.repository.AcademiesByLocation;
import org.guzzing.studayserver.domain.academy.repository.AcademyByFiltering;
import org.guzzing.studayserver.domain.academy.repository.AcademyFilterCondition;

public interface AcademyQueryRepository {

    List<AcademiesByLocation> findAcademiesByLocation(String pointFormat, Long memberId);

    List<AcademyByFiltering> filterAcademies(AcademyFilterCondition academyFilterCondition, Long memberId);
}
