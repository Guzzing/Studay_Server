package org.guzzing.studayserver.domain.member.service;

import java.util.ArrayList;
import java.util.List;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam;
import org.guzzing.studayserver.domain.member.service.param.MemberRegisterParam.MemberRegisterChildInfoParam;
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
        Member member = memberRepository.findById(param.memberId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디입니다."));

        List<Child> children = new ArrayList<>();
        for (MemberRegisterChildInfoParam childInfoParam : param.children()) {
            children.add(new Child(childInfoParam.nickname(), childInfoParam.grade(), member));
        }

        member.update(param.nickname(), param.email());
        for (Child child : children) {
            member.addChild(child);
        }
        return member.getId();
    }
}
