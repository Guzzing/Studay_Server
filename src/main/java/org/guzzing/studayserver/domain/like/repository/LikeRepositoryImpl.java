package org.guzzing.studayserver.domain.like.repository;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.member.model.Member;
import org.springframework.stereotype.Repository;

@Repository
public class LikeRepositoryImpl implements LikeRepository{

    private final LikeJpaRepository likeJpaRepository;

    public LikeRepositoryImpl(LikeJpaRepository likeJpaRepository) {
        this.likeJpaRepository = likeJpaRepository;
    }

    @Override
    public Like save(Like like) {
        return likeJpaRepository.save(like);
    }

    @Override
    public void deleteById(long likeId) {
        likeJpaRepository.deleteById(likeId);
    }

    @Override
    public void deleteByMember(Member member) {
        likeJpaRepository.deleteByMember(member);
    }

    @Override
    public boolean existsById(long id) {
        return likeJpaRepository.existsById(id);
    }

    @Override
    public List<Like> findByMember(Member member) {
        return likeJpaRepository.findByMember(member);
    }

    @Override
    public long countByMember(Member member) {
        return likeJpaRepository.countByMember(member);
    }

    @Override
    public boolean existsByMemberAndAcademy(Member member, Academy academy) {
        return likeJpaRepository.existsByMemberAndAcademy(member, academy);
    }

    @Override
    public void deleteByMemberAndAcademy(Member member, Academy academy) {
        likeJpaRepository.deleteByMemberAndAcademy(member, academy);
    }
}
