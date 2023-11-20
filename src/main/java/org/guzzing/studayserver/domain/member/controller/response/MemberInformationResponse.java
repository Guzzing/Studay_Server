package org.guzzing.studayserver.domain.member.controller.response;

import java.util.List;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult.MemberChildInformationResult;

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
        List<MemberChildInformationResponse> childInformationResponses = result.childResults().stream()
                .map(MemberChildInformationResponse::from)
                .toList();

        return new MemberInformationResponse(result.nickname(), result.email(), childInformationResponses);
    }

    public record MemberChildInformationResponse(
            Long childId,
            String childName,
            String schedule
    ) {

        static MemberChildInformationResponse from(MemberChildInformationResult result) {
            return new MemberChildInformationResponse(result.childId(), result.childName(), result.schedule());
        }
    }
}
