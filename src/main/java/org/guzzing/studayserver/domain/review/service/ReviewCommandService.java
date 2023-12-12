package org.guzzing.studayserver.domain.review.service;

import org.guzzing.studayserver.domain.review.event.NewReviewEvent;
import org.guzzing.studayserver.domain.review.model.Review;
import org.guzzing.studayserver.domain.review.repository.ReviewRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ReviewCommandService(
            final ReviewRepository reviewRepository,
            final ApplicationEventPublisher eventPublisher
    ) {
        this.reviewRepository = reviewRepository;
        this.eventPublisher = eventPublisher;
    }

    public Review saveReview(final Review review) {
        final Review savedReview = reviewRepository.save(review);

        eventPublisher.publishEvent(NewReviewEvent.of(savedReview));

        return savedReview;
    }

    public void deleteReviewOfMember(final long memberId) {
        reviewRepository.deleteByMemberId(memberId);
    }

}
