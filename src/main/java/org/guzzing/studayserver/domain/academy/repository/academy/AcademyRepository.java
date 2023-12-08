package org.guzzing.studayserver.domain.academy.repository.academy;

import java.util.List;
import java.util.Optional;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByFilterWithScroll;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByLocation;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByLocationWithScroll;
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

    boolean existsById(Long academyId);

    void deleteAll();

    Optional<Academy> findAcademyById(Long academyId);

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
