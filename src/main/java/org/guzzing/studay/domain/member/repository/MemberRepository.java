package org.guzzing.studay.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.guzzing.studay.domain.member.model.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findBySocialId(String socialId);

    @Query("select m from Member m where m.socialId= ?1")
    Optional<Member> findMemberIfExisted(String socialId);

}
