package org.guzzing.studayserver.domain.child.service;

import java.util.ArrayList;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.child.repository.ChildRepository;
import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ChildService {

    private final MemberRepository memberRepository;
    private final ChildRepository childRepository;

    public ChildService(MemberRepository memberRepository, ChildRepository childRepository) {
        this.memberRepository = memberRepository;
        this.childRepository = childRepository;
    }

    public Long create(ChildCreateParam param) {
        Member foundMember = getMember(param);

        Child savedChild = childRepository.save(new Child(param.nickname(), param.grade(), foundMember));

        return savedChild.getId();
    }

    public ChildrenFindResult findByMemberId(Long memberId) {
        return new ChildrenFindResult(new ArrayList<>());
    }

    private Member getMember(ChildCreateParam param) {
        return memberRepository.findById(param.memberId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 멤버 아이디입니다: " + param.memberId()));
    }
}
