package org.guzzing.studayserver.domain.member.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam.MemberRegisterChildInfoParam;

public record MemberRegisterRequest(
        @NotBlank(message = "이름은 빈 값일 수 없습니다.")
        @Size(min = 1, max = 10, message = "이름의 길이는 1-10자 사이여야 합니다.")
        String nickname,

        @NotBlank(message = "이메일은 빈 값일 수 없습니다.")
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        String email,

        @NotNull
        @Size(min = 1, max = 5, message = "아이는 최소 1명에서 최대 5명까지만 등록이 가능합니다.")
        @Valid
        List<MemberRegisterChildRequest> children
) {

    public MemberRegisterParam toParam(Long memberId) {
        List<MemberRegisterChildInfoParam> childParamList =
                this.children.stream()
                        .map(child -> new MemberRegisterChildInfoParam(child.nickname(), child.grade()))
                        .toList();

        return new MemberRegisterParam(memberId, nickname, email, childParamList);
    }

    public record MemberRegisterChildRequest(
            @NotBlank(message = "아이의 이름은 빈 값일 수 없습니다.")
            @Size(min = 1, max = 10, message = "아이 이름의 길이는 1-10자 사이여야 합니다.")
            String nickname,

            @NotBlank(message = "아이의 학년은 빈 값일 수 없습니다.")
            String grade
    ) {

    }
}

