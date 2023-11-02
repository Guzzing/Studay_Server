package org.guzzing.studayserver.domain.like.service;

import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.domain.like.service.dto.LikeParam;
import org.guzzing.studayserver.domain.like.service.dto.LikeResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Transactional
    public LikeResult createLikeOfAcademy(LikeParam param) {
        // todo : 학원 아이디가 실제로 존재하는지 확인

        Like savedLike = likeRepository.save(
                Like.of(param.memberId(), param.academyId()));

        return LikeResult.from(savedLike);
    }
}
