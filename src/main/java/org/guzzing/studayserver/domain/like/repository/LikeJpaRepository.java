package org.guzzing.studayserver.domain.like.repository;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepository extends JpaRepository<Like, Long>, LikeRepository {

    List<Like> findByMember(final Member member);

    long countByMember(final Member member);

    boolean existsByMemberAndAcademy(final Member member, final Academy academy);

    void deleteByMemberAndAcademy(final Member member, final Academy academy);

}
