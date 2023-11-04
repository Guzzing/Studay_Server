package org.guzzing.studayserver.domain.child.service;

import java.util.List;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.child.repository.ChildRepository;
import org.guzzing.studayserver.domain.child.service.param.ChildCreateParam;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult;
import org.guzzing.studayserver.domain.child.service.result.ChildrenFindResult.ChildFindResult;
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

    @Transactional
    public Long create(ChildCreateParam param) {
        Member member = getMember(param.memberId());

        Child savedChild = childRepository.save(new Child(param.nickname(), param.grade()));
        savedChild.assignToNewMemberOnly(member);

        return savedChild.getId();
    }

    @Transactional
    public ChildrenFindResult findByMemberId(Long memberId) {
        Member member = getMember(memberId);
        List<Child> children = member.getChildren();

        return new ChildrenFindResult(children.stream()
                .map(child -> new ChildFindResult(child.getId(), child.getNickName(), child.getGrade(), "휴식 중!"))
                .toList());
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 멤버 아이디입니다: " + memberId));
    }
}
