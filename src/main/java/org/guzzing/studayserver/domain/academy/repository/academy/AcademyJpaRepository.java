package org.guzzing.studayserver.domain.academy.repository.academy;

import jakarta.persistence.EntityNotFoundException;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademyJpaRepository extends JpaRepository<Academy, Long>, AcademyRepository {

    default Academy getById(Long academyId) {
        return findById(academyId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

}
