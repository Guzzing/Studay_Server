package org.guzzing.studayserver.domain.academy.repository.academy;

import java.util.Optional;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.repository.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface AcademyRepository {

    Academy getById(Long academyId);

    Academy save(Academy academy);

    Slice<AcademiesByName> findAcademiesByName(String academyName, Pageable pageable);

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

    AcademyByLocationWithCursorRepositoryResponse findAcademiesByLocationByCursor(
        AcademyByLocationWithCursorRepositoryRequest request);
}
