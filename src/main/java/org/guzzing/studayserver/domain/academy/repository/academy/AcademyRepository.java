package org.guzzing.studayserver.domain.academy.repository.academy;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.repository.AcademiesByLocation;
import org.guzzing.studayserver.domain.academy.repository.AcademiesByName;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademyRepository extends AcademyJpaRepository, AcademyQueryRepository {

    Academy getById(Long academyId);

    Academy save(Academy academy);

    boolean existsById(final Long academyId);
  
    Slice<AcademiesByName> findAcademiesByName(String academyName, Pageable pageable);

    List<AcademiesByLocation> findAcademiesByLocation(String pointFormat);

}
