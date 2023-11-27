package org.guzzing.studayserver.domain.member.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.guzzing.studayserver.domain.child.controller.request.ChildCreateRequest;
import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam;

public record MemberRegisterRequest(
        @NotBlank(message = "이름은 빈 값일 수 없습니다.")
        @Size(min = 1, max = 10, message = "이름의 길이는 1-10자 사이여야 합니다.")
        String nickname,

        @NotBlank(message = "이메일은 빈 값일 수 없습니다.")
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        String email,

        @NotNull
        @Size(min = 1, max = 5, message = "아이는 최소 1명에서 최대 5명까지 등록이 가능합니다.")
        @Valid
        List<ChildCreateRequest> children
) {

    public MemberRegisterParam toParam() {
        List<ChildCreateParam> childParams =
                this.children.stream()
                        .map(ChildCreateRequest::toParam)
                        .toList();

        return new MemberRegisterParam(nickname, email, childParams);
    }
}

