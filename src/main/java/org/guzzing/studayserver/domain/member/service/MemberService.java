package org.guzzing.studayserver.domain.member.service;

import java.util.List;
import org.guzzing.studayserver.domain.child.service.ChildService;
import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult;
import org.guzzing.studayserver.domain.member.service.result.MemberInformationResult.MemberChildInformationResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ChildService childService;

    public MemberService(MemberRepository memberRepository, ChildService childService) {
        this.memberRepository = memberRepository;
        this.childService = childService;
    }

    @Transactional
    public Long register(MemberRegisterParam param, Long memberId) {
        Member member = getMember(memberId);

        member.update(param.nickname(), param.email());

        for (ChildCreateParam childParam : param.children()) {
            childService.create(childParam, memberId);
        }

        return member.getId();
    }

    @Transactional
    public void remove(final long memberId) {
        memberRepository.deleteById(memberId);
    }

    public MemberInformationResult getById(Long memberId) {
        Member member = getMember(memberId);

        List<MemberChildInformationResult> childInformationResults = member.getChildren().stream()
                .map(child -> new MemberChildInformationResult(child.getId(),
                        child.getNickName(),
                        child.getProfileImageURLPath(),
                        "휴식중!!"))
                .toList();

        return new MemberInformationResult(member.getNickName(), member.getEmail(), childInformationResults);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디입니다."));
    }
}
