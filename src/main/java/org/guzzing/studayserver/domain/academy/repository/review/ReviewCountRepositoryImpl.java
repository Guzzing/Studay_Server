package org.guzzing.studayserver.domain.academy.repository.review;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewCountRepositoryImpl implements ReviewCountRepository {

    private final ReviewCountJpaRepository reviewCountJpaRepository;

    public ReviewCountRepositoryImpl(ReviewCountJpaRepository reviewCountJpaRepository) {
        this.reviewCountJpaRepository = reviewCountJpaRepository;
    }

    @Override
    public Optional<ReviewCount> findByAcademyId(Long academyId) {
        return reviewCountJpaRepository.findByAcademyId(academyId);
    }

    @Override
    public ReviewCount getByAcademyId(Long academyId) {
        return reviewCountJpaRepository.findByAcademyId(academyId)
                .orElseThrow(() -> new EntityNotFoundException("해당 학원에 대한 리뷰 카운트가 존재하지 않습니다."));
    }

    @Override
    public ReviewCount save(ReviewCount reviewCount) {
        return reviewCountJpaRepository.save(reviewCount);
    }

    @Override
    public void deleteAll() {
        reviewCountJpaRepository.deleteAll();
    }
}
