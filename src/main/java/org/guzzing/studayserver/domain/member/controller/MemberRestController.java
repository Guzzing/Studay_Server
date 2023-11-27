package org.guzzing.studayserver.domain.member.controller;

import jakarta.validation.Valid;
import org.guzzing.studayserver.domain.auth.memberId.MemberId;
import org.guzzing.studayserver.domain.member.controller.request.MemberRegisterRequest;
import org.guzzing.studayserver.domain.member.controller.response.MemberInformationResponse;
import org.guzzing.studayserver.domain.member.service.MemberService;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberRestController {

    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
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
