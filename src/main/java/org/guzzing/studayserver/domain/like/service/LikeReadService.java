package org.guzzing.studayserver.domain.like.service;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.global.exception.LikeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LikeReadService {

    private final LikeRepository likeRepository;

    public LikeReadService(final LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public boolean isLikedAcademy(final Member member, final Academy academy) {
        return likeRepository.existsByMemberAndAcademy(member, academy);
    }

    public List<Like> findAllLikesOfMember(final Member member) {
        return likeRepository.findByMember(member);
    }

    protected void validateLikeLimit(final Member member) {
        final long likeCount = likeRepository.countByMember(member);

        if (likeCount >= 10) {
            throw new LikeException("좋아요 개수는 10개를 넘을 수 없습니다.");
        }
    }

    protected void validateExistsLike(final Member member, final Academy academy) {
        final boolean existsLike = likeRepository.existsByMemberAndAcademy(member, academy);

        if (existsLike) {
            throw new LikeException("이미 좋아요한 학원입니다.");
        }
    }

}
