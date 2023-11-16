package org.guzzing.studayserver.domain.academy.listener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

import java.util.List;
import java.util.Map;
import org.guzzing.studayserver.domain.academy.repository.review.ReviewCountRepository;
import org.guzzing.studayserver.domain.review.event.NewReviewEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NewReviewListener {

    private final ReviewCountRepository reviewCountRepository;

    public NewReviewListener(final ReviewCountRepository reviewCountRepository) {
        this.reviewCountRepository = reviewCountRepository;
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void updateReviewCount(final NewReviewEvent event) {
        final long academyId = event.getAcademyId();
        final List<String> reviews = event.getReviews();
        final Map<NewReviewType, Integer> newReview = NewReviewType.newReviewCountOf(reviews);

        reviewCountRepository.getByAcademyId(academyId)
                .updateSelectedReviewCount(newReview);
    }

}
