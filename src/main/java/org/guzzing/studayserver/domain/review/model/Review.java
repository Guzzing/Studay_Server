package org.guzzing.studayserver.domain.review.model;

import static jakarta.persistence.FetchType.LAZY;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.member.model.Member;
import org.hibernate.annotations.Type;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "academy_id")
    private Academy academy;

    @Type(JsonType.class)
    @Column(name = "reviewed_type", nullable = false, columnDefinition = "LONGTEXT")
    private Map<String, Boolean> reviewType;

    public Review(
            final Member member,
            final Academy academy,
            final Map<String, Boolean> reviewType
    ) {
        this.member = member;
        this.academy = academy;
        this.reviewType = reviewType;
    }

    public static Review of(
            final Member member,
            final Academy academy,
            final Map<ReviewType, Boolean> reviewType
    ) {
        final Map<String, Boolean> selectedRevieType = reviewType.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        Entry::getValue
                ));

        return new Review(member, academy, selectedRevieType);
    }

    public long getMemberId() {
        return this.member.getId();
    }

    public long getAcademyId() {
        return this.academy.getId();
    }

}
