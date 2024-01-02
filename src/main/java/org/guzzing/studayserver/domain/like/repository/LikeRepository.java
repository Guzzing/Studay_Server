package org.guzzing.studayserver.domain.like.repository;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.member.model.Member;

public interface LikeRepository {

    Like save(final Like like);

    void deleteById(final long likeId);

    boolean existsById(final long id);

    List<Like> findByMember(final Member member);

    long countByMember(final Member member);

    boolean existsByMemberAndAcademy(final Member member, final Academy academy);

    void deleteByMemberAndAcademy(final Member member, final Academy academy);

}
