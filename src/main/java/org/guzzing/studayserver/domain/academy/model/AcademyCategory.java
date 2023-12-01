package org.guzzing.studayserver.domain.academy.model;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Table(name = "academy_categories")
@Entity
public class AcademyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "academy_id")
    Academy academy;

    @Column(name = "category_id")
    Long categoryId;

    protected AcademyCategory() {
    }

    private AcademyCategory(
            final Academy academy,
            final Long categoryId) {
        this.academy = academy;
        this.categoryId = categoryId;
    }

    public static AcademyCategory of(
            final Academy academy,
            final Long categoryId) {
        return new AcademyCategory(
                academy,
                categoryId);
    }

}
