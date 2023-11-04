package org.guzzing.studayserver.domain.child.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.guzzing.studayserver.domain.member.model.Member;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "children")
@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id")
    private Long id;

    @Embedded
    @Column(nullable = false)
    private NickName nickName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Child(String nickName, String grade) {
        this.nickName = new NickName(nickName);
        this.grade = Grade.fromDescription(grade);
    }

    public void assignToNewMemberOnly(Member member) {
        if (this.member != null) {
            throw new IllegalStateException("이미 멤버가 할당되어 있습니다.");
        }

        this.member = member;
        member.addChild(this);
    }

    public Long getId() {
        return id;
    }

    public String getNickName() {
        return nickName.getValue();
    }

    public String getGrade() {
        return grade.getDescription();
    }
}
