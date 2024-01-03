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
import java.util.Objects;
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
    private ProfileImageUri profileImageUri;

    @Embedded
    @Column(nullable = false, name = "nick_name")
    private ChildNickname nickName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Child(String nickName, String grade, String defaultProfileImageUri) {
        this.nickName = new ChildNickname(nickName);
        this.grade = Grade.fromDescription(grade);
        this.profileImageUri = new ProfileImageUri(defaultProfileImageUri);
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

    public String getProfileImageURLPath() {
        return profileImageUri.getImageURLPath();
    }

    public String getProfileImageURIPath() {
        return profileImageUri.getImageUri();
    }

    public String getNickName() {
        return nickName.getValue();
    }

    public String getGrade() {
        return grade.getDescription();
    }

    public Member getMember() {
        return member;
    }

    public void update(String nickname, String grade) {
        this.nickName = new ChildNickname(nickname);
        this.grade = Grade.fromDescription(grade);
    }

    public void updateProfileImageUri(final String customProfileImageUri) {
        this.profileImageUri = new ProfileImageUri(customProfileImageUri);
    }

    public void increaseGrade() {
        this.grade = Grade.increaseGrade(this.grade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Child child = (Child) o;
        return Objects.equals(id, child.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
