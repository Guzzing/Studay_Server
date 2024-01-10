package org.guzzing.studayserver.domain.member.repository;

import java.util.Optional;
import org.guzzing.studayserver.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository {

    Optional<Member> findMemberIfExisted(String socialId);

    void deleteById(long memberId);

    Optional<Member> findById(Long memberId);

    Member save(Member clientMember);

    boolean existsById(Long memberId);
}
