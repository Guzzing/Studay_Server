package org.guzzing.studayserver.domain.like.service;

import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeAccessServiceImpl implements LikeAccessService {

    private final LikeRepository likeRepository;

    public LikeAccessServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public boolean isLiked(Long academyId, Long memberId) {
        return likeRepository.existsByMemberIdAndAcademyId(memberId, academyId);
    }

}
