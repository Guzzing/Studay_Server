package org.guzzing.studayserver.domain.review.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "academy_id", nullable = false)
    private Long academyId;

    @Column(name = "reviewed_type", nullable = false)
    private List<ReviewType> reviewTypes;

    public Review(
            final Long memberId,
            final Long academyId,
            final List<ReviewType> reviewTypes
    ) {
        this.memberId = memberId;
        this.academyId = academyId;
        this.reviewTypes = reviewTypes;
    }

    public static Review of(
            final Long memberId,
            final Long academyId,
            final Map<ReviewType, Boolean> selectedReviewMap
    ) {
        List<ReviewType> reviewTypes = ReviewType.convertReviewMapToReviewList(selectedReviewMap);

        return new Review(memberId, academyId, reviewTypes);
    }

}
