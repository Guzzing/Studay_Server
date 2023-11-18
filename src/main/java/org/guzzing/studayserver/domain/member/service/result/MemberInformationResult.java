package org.guzzing.studayserver.domain.member.service.result;

import java.util.List;

public record MemberInformationResult(
        String nickname,
        String email,
        List<MemberChildInformationResult> memberChildInformationResults
) {

    public MemberInformationResult(String nickname, String email,
            List<MemberChildInformationResult> memberChildInformationResults) {
        this.nickname = nickname;
        this.email = email;
        this.memberChildInformationResults = List.copyOf(memberChildInformationResults);
    }

    public record MemberChildInformationResult(
            Long childId,
            String childName,
            String schedule
    ) {

    }

}
