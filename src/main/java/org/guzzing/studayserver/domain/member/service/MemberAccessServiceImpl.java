package org.guzzing.studayserver.domain.member.service;

import java.util.List;
import org.guzzing.studayserver.domain.child.model.Child;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.repository.MemberRepository;
import org.guzzing.studayserver.global.exception.ChildException;
import org.guzzing.studayserver.global.exception.MemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberAccessServiceImpl implements
        MemberAccessService {

    private final MemberRepository memberRepository;

    public MemberAccessServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void validateMember(final Long memberId) {
        final boolean existsMember = memberRepository.existsById(memberId);

        if (!existsMember) {
            throw new MemberException("존재하지 않는 멤버입니다.");
        }
    }

    @Override
    public void validateChild(final Long memberId, final Long childId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException("존재하지 않는 멤버입니다."));

        final List<Long> childrenIds = member.getChildren()
                .stream()
                .map(Child::getId)
                .toList();

        if (!childrenIds.contains(childId)) {
            throw new ChildException("해당 멤버의 아이가 아닙니다.");
        }
    }
}
