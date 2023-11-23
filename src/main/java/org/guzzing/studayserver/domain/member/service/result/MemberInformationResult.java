package org.guzzing.studayserver.domain.member.service.result;

import java.util.List;

public record MemberInformationResult(
        String nickname,
        String email,
        List<MemberChildInformationResult> childResults
) {

    public MemberInformationResult(String nickname, String email,
            List<MemberChildInformationResult> childResults) {
        this.nickname = nickname;
        this.email = email;
        this.childResults = List.copyOf(childResults);
    }

    public record MemberChildInformationResult(
            Long childId,
            String childName,
            String profileImageUrlPath,
            String schedule
    ) {

    }

}
