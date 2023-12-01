package org.guzzing.studayserver.domain.review.event;

import java.util.Map;
import org.guzzing.studayserver.domain.review.model.Review;

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

    public Map<String, Boolean> getReviewType() {
        return this.review.getReviewType();
    }

}
