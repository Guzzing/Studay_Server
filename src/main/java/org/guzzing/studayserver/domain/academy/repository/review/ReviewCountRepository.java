package org.guzzing.studayserver.domain.academy.repository.review;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;

public interface ReviewCountRepository {

    Optional<ReviewCount> findByAcademyId(Long academyId);

    ReviewCount getByAcademyId(Long academyId);

    ReviewCount save(ReviewCount reviewCount);

    void deleteAll();
}
