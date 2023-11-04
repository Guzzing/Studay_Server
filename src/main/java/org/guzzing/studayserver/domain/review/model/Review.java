package org.guzzing.studayserver.domain.review.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "kindness", nullable = false)
    private boolean kindness;

    @Column(name = "good_facility", nullable = false)
    private boolean goodFacility;

    @Column(name = "cheap_fee", nullable = false)
    private boolean cheapFee;

    @Column(name = "good_management", nullable = false)
    private boolean goodManagement;

    @Column(name = "lovely_teaching", nullable = false)
    private boolean lovelyTeaching;

    protected Review(
            final boolean kindness,
            final boolean goodFacility,
            final boolean cheapFee,
            final boolean goodManagement,
            final boolean lovelyTeaching
    ) {
        this.kindness = kindness;
        this.goodFacility = goodFacility;
        this.cheapFee = cheapFee;
        this.goodManagement = goodManagement;
        this.lovelyTeaching = lovelyTeaching;
    }

}
