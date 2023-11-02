package org.guzzing.studayserver.domain.academy.repository.review;

import org.guzzing.studayserver.domain.academy.model.ReviewCount;

public interface ReviewCountRepository extends ReviewCountJpaRepository {

    ReviewCount getByAcademyId(Long academyId);
}
