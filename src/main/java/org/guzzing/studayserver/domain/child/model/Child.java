package org.guzzing.studayserver.domain.child.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.guzzing.studayserver.domain.member.model.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id")
    private Long id;

    @Embedded
    @Column
    private NickName nickName;

    private String grade;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Child(String nickName, String grade, Member member) {
        this.nickName = new NickName(nickName);
        this.grade = grade;
        this.member = member;
    }
}
