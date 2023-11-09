package org.guzzing.studayserver.domain.like.service;

import org.springframework.stereotype.Service;

@Service
public class LikeAccessServiceImpl implements LikeAccessService {
    @Override
    public boolean isLiked(Long academyId, Long memberId) {
        return false;
    }
}
