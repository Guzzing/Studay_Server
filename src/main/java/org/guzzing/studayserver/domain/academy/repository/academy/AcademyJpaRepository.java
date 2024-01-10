package org.guzzing.studayserver.domain.academy.repository.academy;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademiesByName;
import org.guzzing.studayserver.domain.academy.repository.dto.AcademyFee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AcademyJpaRepository extends JpaRepository<Academy, Long> {

    @Query(value =
            "SELECT a.id AS academyId, a.academy_name AS academyName, a.full_address AS fullAddress, a.latitude, a.longitude "
                    +
                    "FROM academies As a " +
                    "WHERE MATCH(a.academy_name) AGAINST(:academyName IN BOOLEAN MODE)",
            countQuery = "SELECT COUNT(a.id) FROM academies As a WHERE MATCH(a.academy_name) AGAINST(:academyName IN BOOLEAN MODE)",
            nativeQuery = true)
    Slice<AcademiesByName> findAcademiesByName(@Param("academyName") String academyName, Pageable pageable);


    @Query("SELECT a FROM Academy AS a WHERE a.id = :academyId")
    AcademyFee findAcademyFeeInfo(@Param("academyId") Long academyId);

    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM Academy a WHERE a.id = :academyId) THEN true ELSE false END")
    boolean existsById(@Param("academyId") Long academyId);

}
