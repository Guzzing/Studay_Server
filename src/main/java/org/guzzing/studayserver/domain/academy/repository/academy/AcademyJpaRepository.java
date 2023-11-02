package org.guzzing.studayserver.domain.academy.repository.academy;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcademyJpaRepository extends JpaRepository<Academy, Long> {

    Optional<Academy> findById(Long academyId);

}
