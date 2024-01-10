package org.guzzing.studayserver.domain.member.repository;

import java.util.Optional;
import org.guzzing.studayserver.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.socialId= ?1")
    Optional<Member> findMemberIfExisted(String socialId);

}
