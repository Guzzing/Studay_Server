package org.guzzing.studayserver.domain.academy.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="review_counts")
public class ReviewCount {

    private static final int INIT_VALUE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private int kindnessCount;

    @Column(nullable = false)
    private int goodFacilityCount;

    @Column(nullable = false)
    private int cheapFeeCount;

    @Column(nullable = false)
    private int goodManagementCount;

    @Column(nullable = false)
    private int lovelyTeachingCount;

    @Column(nullable = false)
    private int reviewersCount;

    @OneToOne
    @JoinColumn(name = "academies_id")
    private Academy academy;

    public int makePercent(int reviewCount){
        if(reviewersCount==0) {
            return INIT_VALUE;
        }

        return reviewCount/reviewersCount * 100 ;
    }

    protected ReviewCount() {

    }
    protected ReviewCount( int kindnessCount, int goodFacilityCount, int cheapFeeCount, int goodManagementCount, int lovelyTeachingCount, int reviewersCount, Academy academy) {
        this.kindnessCount = kindnessCount;
        this.goodFacilityCount = goodFacilityCount;
        this.cheapFeeCount = cheapFeeCount;
        this.goodManagementCount = goodManagementCount;
        this.lovelyTeachingCount = lovelyTeachingCount;
        this.reviewersCount = reviewersCount;
        this.academy = academy;
    }

    public static ReviewCount makeDefaultReviewCount(Academy academy) {
        return new ReviewCount(INIT_VALUE,INIT_VALUE,INIT_VALUE,INIT_VALUE,INIT_VALUE,INIT_VALUE,academy);
    }

}