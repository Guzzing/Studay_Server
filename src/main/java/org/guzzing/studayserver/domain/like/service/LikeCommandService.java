package org.guzzing.studayserver.domain.like.service;

import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LikeCommandService {

    private final LikeRepository likeRepository;

    public LikeCommandService(final LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like saveLike(final long memberId, final long academyId) {
        final Like like = Like.of(memberId, academyId);
        return likeRepository.save(like);
    }

    public void deleteLike(final long likeId) {
        likeRepository.deleteById(likeId);
    }

    public void deleteLikesOfMember(final long memberId) {
        likeRepository.deleteByMemberId(memberId);
    }

    public void deleteLikesOfAcademyAndMember(final long academyId, final long memberId) {
        likeRepository.deleteByAcademyIdAndMemberId(academyId, memberId);
    }

}
