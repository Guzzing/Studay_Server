package org.guzzing.studayserver.domain.academy.repository.academy;

import org.guzzing.studayserver.domain.academy.repository.AcademiesByLocation;

import java.util.List;

public interface AcademyQueryRepository {
    List<AcademiesByLocation> findAcademiesByLocation(String pointFormat);
}
