package org.guzzing.studayserver.domain.child.repository;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.global.error.response.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {

    Optional<Child> findByIdAndMemberId(Long childId, Long memberId);

    void deleteByMemberId(final long memberId);
}
