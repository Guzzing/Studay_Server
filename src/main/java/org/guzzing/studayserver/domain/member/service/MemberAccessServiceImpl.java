package org.guzzing.studayserver.domain.member.service;

import org.guzzing.studayserver.domain.member.repository.MemberRepository;
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
    public boolean existsMember(Long memberId) {
        return memberRepository.existsById(memberId);
    }
}
