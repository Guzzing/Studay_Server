package org.guzzing.studayserver.domain.member.repository;

import java.util.Optional;
import org.guzzing.studayserver.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findBySocialId(String socialId);

    @Query("select m from Member m where m.socialId= ?1")
    Optional<Member> findMemberIfExisted(String socialId);

}
