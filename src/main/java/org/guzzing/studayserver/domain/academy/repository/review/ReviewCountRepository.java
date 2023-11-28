package org.guzzing.studayserver.domain.academy.repository.review;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import org.guzzing.studayserver.domain.academy.model.ReviewCount;

public interface ReviewCountRepository {

    Optional<ReviewCount> findByAcademyId(Long academyId);

    default ReviewCount getByAcademyId(Long academyId) {
        return this.findByAcademyId(academyId)
                .orElseThrow(() -> new EntityNotFoundException("해당 학원에 대한 리뷰 카운트가 존재하지 않습니다."));
    }

    ReviewCount save(ReviewCount reviewCount);

    void deleteAll();
}
