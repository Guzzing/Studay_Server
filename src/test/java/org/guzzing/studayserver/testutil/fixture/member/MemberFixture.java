package org.guzzing.studayserver.testutil.fixture.member;

import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.NickName;
import org.guzzing.studayserver.domain.member.model.vo.MemberProvider;
import org.guzzing.studayserver.domain.member.model.vo.RoleType;

public class MemberFixture {

    public static Member member() {
        return Member.of(new NickName("나는왕이다"), "12345678", MemberProvider.KAKAO, RoleType.USER);
    }

}
