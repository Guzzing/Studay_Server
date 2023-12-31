package org.guzzing.studayserver.domain.member.controller;

import jakarta.validation.Valid;
import org.guzzing.studayserver.domain.auth.member_id.MemberId;
import org.guzzing.studayserver.domain.member.controller.request.MemberRegisterRequest;
import org.guzzing.studayserver.domain.member.controller.response.MemberInformationResponse;
import org.guzzing.studayserver.domain.member.service.MemberFacade;
import org.guzzing.studayserver.domain.member.service.MemberService;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberRestController {

    private final MemberService memberService;
    private final MemberFacade memberFacade;

    public MemberRestController(MemberService memberService, MemberFacade memberFacade) {
        this.memberService = memberService;
        this.memberFacade = memberFacade;
    }

    @PatchMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Long> register(@MemberId Long memberId, @RequestBody @Valid MemberRegisterRequest request) {
        Long registeredMemberId = memberService.register(request.toParam(), memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(registeredMemberId);
    }

    /**
     * 회원탈퇴
     * <p>
     * 1. 회원 정보 제거 2. 아이 정보 제거 3. 학원 타임 템플릿 제거 3-1. 학원 스케줄 제거 4. 대시보드 제거 4-1. 대시보드 스케줄 5. 좋아요 제거 6. 리뷰 제거
     *
     * @param memberId
     * @return void
     */
    @DeleteMapping
    public ResponseEntity<Void> remove(
            @MemberId final Long memberId
    ) {
        memberFacade.removeMember(memberId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MemberInformationResponse> getInformation(@MemberId Long memberId) {
        MemberInformationResult result = memberService.getById(memberId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MemberInformationResponse.from(result));
    }
}
