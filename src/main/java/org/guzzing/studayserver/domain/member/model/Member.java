package org.guzzing.studayserver.domain.member.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import lombok.Getter;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.member.model.vo.Email;
import org.guzzing.studayserver.domain.member.model.vo.NickName;
import org.guzzing.studayserver.global.common.auth.OAuth2Provider;
import org.guzzing.studayserver.global.common.auth.RoleType;

@Getter
@Table(name = "members", uniqueConstraints = @UniqueConstraint(columnNames = {"social_Id", "member_provider"}))
@Entity
public class Member {

    public static final int CHILDREN_MAX_SIZE = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    @Column(name = "nick_name")
    private NickName nickName;

    @Embedded
    @Column
    private Email email;

    @Column(nullable = false, name = "social_id")
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "member_provider")
    private OAuth2Provider OAuth2Provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role_type")
    private RoleType roleType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<Child> children = new ArrayList<>();

    protected Member() {

    }

    protected Member(NickName nickName, String socialId, OAuth2Provider OAuth2Provider, RoleType roleType) {
        this.nickName = nickName;
        this.socialId = socialId;
        this.OAuth2Provider = OAuth2Provider;
        this.roleType = roleType;
    }

    public static Member of(NickName nickName, String socialId, OAuth2Provider OAuth2Provider, RoleType roleType) {
        return new Member(nickName, socialId, OAuth2Provider, roleType);
    }

    public void update(String nickname, String email) {
        this.nickName = new NickName(nickname);
        this.email = new Email(email);
    }

    public void addChild(Child child) {
        if (CHILDREN_MAX_SIZE <= children.size()) {
            throw new IllegalStateException(String.format("멤버당 아이는 최대 %d까지 등록할 수 있습니다.", CHILDREN_MAX_SIZE));
        }

        children.add(child);
    }

    public void removeChild(Long childId) {
        children.removeIf(child -> child.getId().equals(childId));
    }

    public String getNickName() {
        if (nickName == null) {
            return "";
        }

        return nickName.getValue();
    }

    public String getEmail() {
        if (email == null) {
            return "";
        }

        return email.getValue();
    }

    public Optional<Child> findChild(Long childId) {
        return children.stream()
                .filter(child -> child.getId().equals(childId))
                .findFirst();
    }

    public List<Child> getChildren() {
        return new ArrayList<>(children);
    }

    public String getNickname() {
        return nickName.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(nickName, member.nickName)
                && Objects.equals(email, member.email) && Objects.equals(socialId, member.socialId)
                && OAuth2Provider == member.OAuth2Provider && roleType == member.roleType && Objects.equals(
                children, member.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickName, email, socialId, OAuth2Provider, roleType, children);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Member.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("nickName=" + nickName)
                .add("email=" + email)
                .add("socialId='" + socialId + "'")
                .add("OAuth2Provider=" + OAuth2Provider)
                .add("roleType=" + roleType)
                .add("children=" + children)
                .toString();
    }

}
