package org.guzzing.studayserver.domain.academy.repository.academy;

import java.util.List;
import org.guzzing.studayserver.domain.academy.repository.AcademiesByLocation;

public interface AcademyQueryRepository {

    List<AcademiesByLocation> findAcademiesByLocation(String pointFormat);
}
