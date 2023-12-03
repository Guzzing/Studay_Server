package org.guzzing.studayserver.domain.review.repository;

import org.guzzing.studayserver.domain.review.model.Review;

public interface ReviewRepository {

    Review save(final Review review);

    boolean existsByMemberIdAndAcademyId(final Long memberId, final Long academyId);

    void deleteByMemberId(final long memberId);

    void deleteAll();

}
