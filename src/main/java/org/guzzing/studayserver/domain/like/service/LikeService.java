package org.guzzing.studayserver.domain.like.service;

import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.domain.like.service.dto.request.LikePostParam;
import org.guzzing.studayserver.domain.like.service.dto.response.AcademyFeeInfo;
import org.guzzing.studayserver.domain.like.service.dto.response.LikeGetResult;
import org.guzzing.studayserver.domain.like.service.dto.response.LikePostResult;
import org.guzzing.studayserver.domain.like.service.external.AcademyAccessService;
import org.guzzing.studayserver.domain.like.service.external.MemberAccessService;
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
    public LikePostResult createLikeOfAcademy(final LikePostParam param) {
        // todo : 한 멤버 당 10개의 좋아요만 등록 가능
        // todo : 아카데미 모듈, 멤버 모듈 머지 시 연결 요망
//        boolean isExistUser = memberAccessService.existsMember(param.memberId());
//        boolean isExistAcademy = academyAccessService.existsAcademy(param.academyId());
//
//        if (!isExistUser) {
//            throw new RuntimeException("존재하지 않는 멤버입니다.");
//        }
//        if (!isExistAcademy) {
//            throw new RuntimeException("존재하지 않는 학원입니다.");
//        }

        Like savedLike = likeRepository.save(
                Like.of(param.memberId(), param.academyId()));

        return LikePostResult.from(savedLike);
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
