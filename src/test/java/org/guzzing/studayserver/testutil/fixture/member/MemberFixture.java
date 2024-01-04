package org.guzzing.studayserver.testutil.fixture.member;

import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.model.vo.NickName;
import org.guzzing.studayserver.global.common.auth.OAuth2Provider;
import org.guzzing.studayserver.global.common.auth.RoleType;

public class MemberFixture {

    public static Member makeMemberEntity() {
        return Member.of(new NickName("나는왕이다"), "12345678", OAuth2Provider.KAKAO, RoleType.USER);
    }

}
