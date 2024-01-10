package org.guzzing.studayserver.domain.member.repository;

import java.util.Optional;
import org.guzzing.studayserver.domain.member.model.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

    private final MemberJpaRepository memberJpaRepository;

    public MemberRepositoryImpl(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Optional<Member> findMemberIfExisted(String socialId) {
        return memberJpaRepository.findMemberIfExisted(socialId);
    }

    @Override
    public void deleteById(long memberId) {
        memberJpaRepository.deleteById(memberId);
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return memberJpaRepository.findById(memberId);
    }

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public boolean existsById(Long memberId) {
        return memberJpaRepository.existsById(memberId);
    }
}
