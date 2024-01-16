package org.guzzing.studayserver.domain.like.service;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.domain.member.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LikeCommandService {

    private final LikeRepository likeRepository;

    public LikeCommandService(final LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like saveLike(final Member member, final Academy academy) {
        final Like like = Like.of(member, academy);
        return likeRepository.save(like);
    }

    public void deleteLike(final long likeId) {
        likeRepository.deleteById(likeId);
    }

    public void deleteLikesOfAcademyAndMember(final Member member, final Academy academy) {
        likeRepository.deleteByMemberAndAcademy(member, academy);
    }

}
