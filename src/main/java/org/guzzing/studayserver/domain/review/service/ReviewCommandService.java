package org.guzzing.studayserver.domain.review.service;

import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.review.model.Review;
import org.guzzing.studayserver.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewCommandService {

    private final ReviewRepository reviewRepository;

    public ReviewCommandService(
            final ReviewRepository reviewRepository
    ) {
        this.reviewRepository = reviewRepository;
    }

    public Review saveReview(final Review review) {
        return reviewRepository.save(review);
    }

}
