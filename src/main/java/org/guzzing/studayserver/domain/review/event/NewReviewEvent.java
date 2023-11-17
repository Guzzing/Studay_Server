package org.guzzing.studayserver.domain.review.event;

import java.util.List;
import org.guzzing.studayserver.domain.review.model.Review;
import org.guzzing.studayserver.domain.review.model.ReviewType;

public class NewReviewEvent {

    private final Review review;

    private NewReviewEvent(final Review review) {
        this.review = review;
    }

    public static NewReviewEvent of(final Review review) {
        return new NewReviewEvent(review);
    }

    public long getAcademyId() {
        return this.review.getAcademyId();
    }

    public List<String> getReviews() {
        return this.review.getReviewTypes()
                .stream()
                .map(ReviewType::name)
                .toList();
    }

}
