package org.guzzing.studayserver.domain.member.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.guzzing.studayserver.domain.auth.member_id.MemberId;
import org.guzzing.studayserver.domain.member.controller.request.MemberRegisterRequest;
import org.guzzing.studayserver.domain.member.controller.response.MemberInformationResponse;
import org.guzzing.studayserver.domain.member.service.MemberFacade;
import org.guzzing.studayserver.domain.member.service.MemberService;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult;
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

    @PatchMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> register(@MemberId Long memberId, @RequestBody @Valid MemberRegisterRequest request) {
        Long registeredMemberId = memberService.register(request.toParam(), memberId);

        return ResponseEntity
                .status(OK)
                .body(registeredMemberId);
    }

    @DeleteMapping
    public ResponseEntity<Long> withdraw(HttpServletRequest request, @MemberId Long memberId) {
        return ResponseEntity
                .status(NO_CONTENT)
                .body(memberFacade.removeMember(request, memberId));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberInformationResponse> getInformation(@MemberId Long memberId) {
        MemberInformationResult result = memberService.getById(memberId);

        return ResponseEntity
                .status(OK)
                .body(MemberInformationResponse.from(result));
    }
}
