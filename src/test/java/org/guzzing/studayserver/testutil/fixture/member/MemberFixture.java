package org.guzzing.studayserver.testutil.fixture.member;

import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.NickName;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class MemberFixture {

    public static Member makeMemberEntity() {
        return Member.of(new NickName("나는왕이다"), "12345678", MemberProvider.KAKAO, RoleType.USER);
    }

}
