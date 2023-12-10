package org.guzzing.studayserver.domain.like.service;

import java.util.List;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.domain.like.service.dto.request.LikePostParam;
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

    public boolean isLikedAcademy(final long academyId, final long memberId) {
        return likeRepository.existsByMemberIdAndAcademyId(memberId, academyId);
    }

    public List<Like> findAllLikesOfMember(final long memberId) {
        return likeRepository.findByMemberId(memberId);
    }

    protected void validateLikeLimit(final LikePostParam param) {
        final long likeCount = likeRepository.countByMemberId(param.memberId());

        if (likeCount >= 10) {
            throw new LikeException("좋아요 개수는 10개를 넘을 수 없습니다.");
        }
    }

    protected void validateExistsLike(final LikePostParam param) {
        final boolean existsLike = likeRepository.existsByMemberIdAndAcademyId(param.memberId(), param.academyId());

        if (existsLike) {
            throw new LikeException("이미 좋아요한 학원입니다.");
        }
    }

}
