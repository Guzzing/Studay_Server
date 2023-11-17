package org.guzzing.studayserver.domain.academy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;

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
            int lovelyTeachingCount, int reviewersCount, int shuttleAvailabilityCount,  Academy academy) {
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
        return new ReviewCount(INIT_VALUE, INIT_VALUE, INIT_VALUE, INIT_VALUE, INIT_VALUE, INIT_VALUE, INIT_VALUE, academy);
    }

}
