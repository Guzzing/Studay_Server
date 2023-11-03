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
    private final AcademyAccessService academyAccessService;
    private final MemberAccessService memberAccessService;

    public LikeService(
            final LikeRepository likeRepository,
            final AcademyAccessService academyAccessService,
            final MemberAccessService memberAccessService
    ) {
        this.likeRepository = likeRepository;
        this.academyAccessService = academyAccessService;
        this.memberAccessService = memberAccessService;
    }

    @Transactional
    public LikeResult createLikeOfAcademy(final LikeParam param) {
        // todo : 학원 아이디가 실제로 존재하는지 확인

        Like savedLike = likeRepository.save(
                Like.of(param.memberId(), param.academyId()));

        return LikeResult.from(savedLike);
    }

    public void removeLikeOfAcademy(final Long likeId, final Long memberId) {
        // todo : 아카데미 모듈 머지 시 연결 요망.
//        boolean isExistUser = memberAccessService.existsMember(memberId);
//
//        if (!isExistUser) {
//            throw new RuntimeException("존재하지 않는 멤버입니다.");
//        }

        likeRepository.deleteById(likeId);
    }


}
