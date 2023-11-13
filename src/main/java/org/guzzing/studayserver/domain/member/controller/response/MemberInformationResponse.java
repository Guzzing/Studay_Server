package org.guzzing.studayserver.domain.member.controller.response;

import java.util.List;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult;

public record MemberInformationResponse(
        String nickname,
        String email,
        List<MemberChildInformationResponse> childInformationResponses
) {

    public MemberInformationResponse(String nickname, String email,
            List<MemberChildInformationResponse> childInformationResponses) {
        this.nickname = nickname;
        this.email = email;
        this.childInformationResponses = List.copyOf(childInformationResponses);
    }

    public static MemberInformationResponse from(MemberInformationResult result) {
        return new MemberInformationResponse(null, null, null);
    }

    public record MemberChildInformationResponse(
            Long childId,
            String childName,
            String schedule
    ) {

    }
}
