package org.guzzing.studayserver.domain.like.service;

public interface LikeAccessService {

    boolean isLiked(Long academyId, Long memberId);
}
