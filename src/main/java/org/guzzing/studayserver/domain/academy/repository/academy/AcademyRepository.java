package org.guzzing.studayserver.domain.academy.repository.academy;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.repository.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademyRepository extends AcademyJpaRepository, AcademyQueryRepository {

    Academy getById(Long academyId);

    Academy save(Academy academy);

    Slice<AcademiesByName> findAcademiesByName(String academyName, Pageable pageable);

    List<AcademiesByLocation> findAcademiesByLocation(String pointFormat);

    List<AcademyByFiltering> filterAcademies(AcademyFilterCondition academyFilterCondition);

    AcademyFee findAcademyFeeInfo(Long academyId);

    boolean existsByAcademyId(Long academyId);
}
