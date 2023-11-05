package org.guzzing.studayserver.domain.child.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;
import org.guzzing.studayserver.domain.child.service.param.ChildModifyParam;

public record ChildModifyRequest(
        @NotBlank(message = "아이의 이름은 빈 값일 수 없습니다.")
        @Size(min = 1, max = 10, message = "아이 이름의 길이는 1-10자 사이여야 합니다.")
        String nickname,

        @NotBlank(message = "아이의 학년은 빈 값일 수 없습니다.")
        String grade
) {

    public ChildModifyParam toParam(Long memberId, Long childId) {
        return new ChildModifyParam(nickname, grade, memberId, childId);
    }
}
