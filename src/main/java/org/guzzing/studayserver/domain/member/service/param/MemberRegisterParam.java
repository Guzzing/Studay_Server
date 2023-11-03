package org.guzzing.studayserver.domain.member.service.param;

import java.util.List;

public record MemberRegisterParam(
        Long memberId,
        String nickname,
        String email,
        List<MemberRegisterChildInfoParam> children
) {

    public record MemberRegisterChildInfoParam(
            String nickname,
            String grade
    ) {

    }

}
