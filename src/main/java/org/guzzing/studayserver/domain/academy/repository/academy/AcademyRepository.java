package org.guzzing.studayserver.domain.academy.repository.academy;

import jakarta.persistence.EntityNotFoundException;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.global.error.response.ErrorCode;

public interface AcademyRepository extends AcademyJpaRepository {

    default Academy getById(Long academyId) {
        return findById(academyId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

}
