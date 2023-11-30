package org.guzzing.studayserver.domain.child.repository;

import org.guzzing.studayserver.domain.child.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long> {

    void deleteByMemberId(final long memberId);

}
