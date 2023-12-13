package org.guzzing.studayserver.domain.academy.model;

import static org.guzzing.studayserver.domain.review.model.ReviewType.CHEAP_FEE;
import static org.guzzing.studayserver.domain.review.model.ReviewType.GOOD_FACILITY;
import static org.guzzing.studayserver.domain.review.model.ReviewType.GOOD_MANAGEMENT;
import static org.guzzing.studayserver.domain.review.model.ReviewType.KINDNESS;
import static org.guzzing.studayserver.domain.review.model.ReviewType.LOVELY_TEACHING;
import static org.guzzing.studayserver.domain.review.model.ReviewType.SHUTTLE_AVAILABILITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Map;
import lombok.Getter;
import org.guzzing.studayserver.domain.review.model.ReviewType;

@Getter
@Entity
@Table(name = "review_counts")
public class ReviewCount {

    private static final int INIT_VALUE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, name = "kindness_count")
    private int kindnessCount;

    @Column(nullable = false, name = "good_facility_count")
    private int goodFacilityCount;

    @Column(nullable = false, name = "cheap_fee_count")
    private int cheapFeeCount;

    @Column(nullable = false, name = "good_management_count")
    private int goodManagementCount;

    @Column(nullable = false, name = "lovely_teaching_count")
    private int lovelyTeachingCount;

    @Column(nullable = false, name = "shuttle_availability_count")
    private int shuttleAvailabilityCount;

    @Column(nullable = false, name = "reviewers_count")
    private int reviewersCount;

    @OneToOne
    @JoinColumn(name = "academies_id")
    private Academy academy;

    public int makePercent(int reviewCount) {
        if (reviewersCount == 0) {
            return INIT_VALUE;
        }

        return reviewCount / reviewersCount * 100;
    }

    protected ReviewCount() {

    }

    protected ReviewCount(int kindnessCount, int goodFacilityCount, int cheapFeeCount, int goodManagementCount,
            int lovelyTeachingCount, int reviewersCount, int shuttleAvailabilityCount, Academy academy) {
        this.kindnessCount = kindnessCount;
        this.goodFacilityCount = goodFacilityCount;
        this.cheapFeeCount = cheapFeeCount;
        this.goodManagementCount = goodManagementCount;
        this.lovelyTeachingCount = lovelyTeachingCount;
        this.reviewersCount = reviewersCount;
        this.academy = academy;
        this.shuttleAvailabilityCount = shuttleAvailabilityCount;
    }

    public static ReviewCount makeDefaultReviewCount(Academy academy) {
        return new ReviewCount(INIT_VALUE, INIT_VALUE, INIT_VALUE, INIT_VALUE, INIT_VALUE, INIT_VALUE, INIT_VALUE,
                academy);
    }

    public void updateSelectedReviewCount(final Map<ReviewType, Boolean> newReview) {
        final Map<ReviewType, Integer> reviewCountMap = ReviewType.convertToReviewTypeCountMap(newReview);

        this.kindnessCount += reviewCountMap.get(KINDNESS);
        this.goodFacilityCount += reviewCountMap.get(GOOD_FACILITY);
        this.cheapFeeCount += reviewCountMap.get(CHEAP_FEE);
        this.goodManagementCount += reviewCountMap.get(GOOD_MANAGEMENT);
        this.lovelyTeachingCount += reviewCountMap.get(LOVELY_TEACHING);
        this.shuttleAvailabilityCount += reviewCountMap.get(SHUTTLE_AVAILABILITY);
    }
}
