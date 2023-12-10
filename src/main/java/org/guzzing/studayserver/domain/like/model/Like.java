package org.guzzing.studayserver.domain.like.model;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.member.model.Member;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "likes", uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "academy_id"}))
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "academy_id", nullable = false)
    private Academy academy;

    protected Like(final Member member, final Academy academy) {
        this.member = member;
        this.academy = academy;
    }

    public static Like of(final Member member, final Academy academy) {
        return new Like(member, academy);
    }

    public long getMemberId() {
        return this.member.getId();
    }

    public long getAcademyId() {
        return this.academy.getId();
    }

}
