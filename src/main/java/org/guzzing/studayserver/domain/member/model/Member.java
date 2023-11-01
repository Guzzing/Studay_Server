package org.guzzing.studayserver.domain.member.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.NickName;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;

@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    @Column
    private NickName nickName;

    private String email;

    @Column(nullable = false)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberProvider memberProvider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    protected Member() {

    }

    protected Member(Long id, NickName nickName, String email, String socialId, MemberProvider memberProvider,
            RoleType roleType) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.socialId = socialId;
        this.memberProvider = memberProvider;
        this.roleType = roleType;
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

    public String getNickName() {
        return nickName.getNickName();
    }

}
