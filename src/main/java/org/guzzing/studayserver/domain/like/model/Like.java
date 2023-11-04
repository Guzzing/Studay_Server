package org.guzzing.studayserver.domain.like.model;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "academy_id")
    private Long academyId;

    protected Like(final Long memberId, final Long academyId) {
        this.memberId = memberId;
        this.academyId = academyId;
    }

    public static Like of(final Long memberId, final Long academyId) {
        return new Like(memberId, academyId);
    }

}
