package org.guzzing.studayserver.domain.academy.repository.academy;

import jakarta.persistence.EntityNotFoundException;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.repository.AcademiesByLocation;
import org.guzzing.studayserver.domain.academy.repository.AcademiesByName;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AcademyJpaRepository extends JpaRepository<Academy, Long> {

    default Academy getById(Long academyId) {
        return findById(academyId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_ENTITY.getMessage()));
    }

    @Query(value = "SELECT a.id AS academyId, a.academy_name AS academyName, a.full_address AS fullAddress " +
            "FROM academies As a " +
            "WHERE MATCH(a.academy_name) AGAINST(:academyName IN BOOLEAN MODE)",
            countQuery = "SELECT COUNT(a.id) FROM academies As a WHERE MATCH(a.academy_name) AGAINST(:academyName IN BOOLEAN MODE)",
            nativeQuery = true)
    Slice<AcademiesByName> findAcademiesByName(@Param("academyName") String academyName, Pageable pageable);

}
