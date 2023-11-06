package org.guzzing.studayserver.domain.member.service.param;

import java.util.List;

public record MemberRegisterParam(
        Long memberId,
        String nickname,
        String email,
        List<MemberAdditionalChildParam> children
) {

    public record MemberAdditionalChildParam(
            String nickname,
            String grade
    ) {

    }

}
