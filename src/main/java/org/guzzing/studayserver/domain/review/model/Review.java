package org.guzzing.studayserver.domain.review.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

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

    @Type(JsonType.class)
    @Column(name = "reviewed_type", nullable = false, columnDefinition = "LONGTEXT")
    private Map<String, Boolean> reviewType;

    public Review(
            final Long memberId,
            final Long academyId,
            final Map<String, Boolean> reviewType
    ) {
        this.memberId = memberId;
        this.academyId = academyId;
        this.reviewType = reviewType;
    }

    public static Review of(
            final Long memberId,
            final Long academyId,
            final Map<ReviewType, Boolean> reviewType
    ) {
        final Map<String, Boolean> selectedRevieType = reviewType.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        Entry::getValue
                ));

        return new Review(memberId, academyId, selectedRevieType);
    }

}
