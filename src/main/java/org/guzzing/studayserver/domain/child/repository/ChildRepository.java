package org.guzzing.studayserver.domain.child.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.guzzing.studayserver.domain.child.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository {

    Optional<Child> findByIdAndMemberId(Long childId, Long memberId);

    void deleteByMemberId(final long memberId);

    Child save(Child child);

    Optional<Child> findById(Long childId);

    List<Child> findAll();
}
