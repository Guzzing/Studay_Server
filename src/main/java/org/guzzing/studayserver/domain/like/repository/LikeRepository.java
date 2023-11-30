package org.guzzing.studayserver.domain.like.repository;

import java.util.List;
import org.guzzing.studayserver.domain.like.model.Like;

public interface LikeRepository {

    Like save(final Like like);

    void deleteById(final Long likeId);

    void deleteByMemberId(final long memberId);

    void deleteByAcademyId(final Long academyId);

    boolean existsById(final Long id);

    List<Like> findByMemberId(final Long memberId);

    long countByMemberId(final Long memberId);

    boolean existsByMemberIdAndAcademyId(final Long memberId, final Long academyId);

}
