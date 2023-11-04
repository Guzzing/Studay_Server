package org.guzzing.studayserver.domain.member.service;

import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam.MemberAdditionalChildParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long register(MemberRegisterParam param) {
        Member member = findMember(param.memberId());

        member.update(param.nickname(), param.email());

        for (MemberAdditionalChildParam childParam : param.children()) {
            new Child(childParam.nickname(), childParam.grade(), member);
        }

        return member.getId();
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디입니다."));
    }
}
