package org.guzzing.studayserver.domain.academy.repository.academy;

import org.guzzing.studayserver.domain.academy.repository.AcademiesByLocation;
import org.guzzing.studayserver.domain.academy.repository.AcademyByFiltering;
import org.guzzing.studayserver.domain.academy.repository.AcademyFilterCondition;

import java.util.List;

public interface AcademyQueryRepository {
    List<AcademiesByLocation> findAcademiesByLocation(String pointFormat);

    List<AcademyByFiltering> filterAcademies(AcademyFilterCondition academyFilterCondition);
}
