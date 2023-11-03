package org.guzzing.studayserver.domain.like.repository;

import java.util.List;
import org.guzzing.studayserver.domain.like.model.Like;

public interface LikeRepository {

    Like save(final Like like);

    void deleteById(final Long id);

    boolean existsById(final Long id);

    List<Like> findByMemberId(final Long memberId);

    long countByMemberId(final Long memberId);

}
