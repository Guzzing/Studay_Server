package org.guzzing.studayserver.domain.academy.repository.academy;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByLocation;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByName;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyByFiltering;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyFee;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyFilterCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface AcademyRepository {

    Academy getById(Long academyId);

    Academy save(Academy academy);

    Slice<AcademiesByName> findAcademiesByName(String academyName, Pageable pageable);

    List<AcademiesByLocation> findAcademiesByLocation(String pointFormat, Long memberId);

    List<AcademyByFiltering> filterAcademies(AcademyFilterCondition academyFilterCondition, Long memberId);

    AcademyFee findAcademyFeeInfo(Long academyId);

    boolean existsByAcademyId(Long academyId);

    void deleteAll();
}
