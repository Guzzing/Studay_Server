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
import lombok.Getter;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.child.model.NickName;
import org.guzzing.studayserver.domain.member.model.vo.Email;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;

@Getter
@Table(name = "members", uniqueConstraints = @UniqueConstraint(columnNames = {"socialId", "memberProvider"}))
@Entity
public class Member {

    public static final int CHILDREN_MAX_SIZE = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    @Column
    private NickName nickName;

    @Embedded
    @Column
    private Email email;

    @Column(nullable = false)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberProvider memberProvider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<Child> children = new ArrayList<>();

    protected Member() {

    }

    protected Member(NickName nickName, String socialId, MemberProvider memberProvider, RoleType roleType) {
        this.nickName = nickName;
        this.socialId = socialId;
        this.memberProvider = memberProvider;
        this.roleType = roleType;
    }

    public static Member of(NickName nickName, String socialId, MemberProvider memberProvider, RoleType roleType) {
        return new Member(nickName, socialId, memberProvider, roleType);
    }

    public void update(String nickname, String email) {
        this.nickName = new NickName(nickname);
        this.email = new Email(email);
    }

    public void addChild(Child child) {
        if (CHILDREN_MAX_SIZE <= children.size()) {
            throw new IllegalStateException(String.format("멤버당 아이는 최대 %d까지만 등록할 수 있습니다.", CHILDREN_MAX_SIZE));
        }

        children.add(child);
    }

    public String getNickName() {
        return nickName.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }
}
