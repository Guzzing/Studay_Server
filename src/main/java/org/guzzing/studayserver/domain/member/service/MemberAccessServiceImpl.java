package org.guzzing.studayserver.domain.member.service;

import org.guzzing.studayserver.domain.member.repository.MemberRepository;
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
}
