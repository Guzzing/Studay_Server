package org.guzzing.studayserver.domain.academy.repository.review;

import org.guzzing.studayserver.domain.academy.model.ReviewCount;

public interface ReviewCountRepository {

    ReviewCount getByAcademyId(Long academyId);

    ReviewCount save(ReviewCount reviewCount);

    void deleteAll();
}
