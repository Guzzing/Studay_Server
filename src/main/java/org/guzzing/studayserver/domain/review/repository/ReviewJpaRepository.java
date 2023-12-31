package org.guzzing.studayserver.domain.review.repository;

import org.guzzing.studayserver.domain.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long>, ReviewRepository {

    boolean existsByMemberIdAndAcademyId(final Long memberId, final Long academyId);

    void deleteByMemberId(final long memberId);

}
