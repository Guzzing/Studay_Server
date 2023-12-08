package org.guzzing.studayserver.domain.child.repository;

import java.util.Optional;
import org.guzzing.studayserver.domain.child.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {

    Optional<Child> findByIdAndMemberId(Long childId, Long memberId);

    void deleteByMemberId(final long memberId);
}
