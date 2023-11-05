package org.guzzing.studayserver.domain.child.service.param;

public record ChildModifyParam(
        String nickname,
        String grade,
        Long memberId,
        Long childId
) {

}
