package org.guzzing.studayserver.domain.like.service;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyFeeInfo;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.service.dto.request.LikePostParam;
import org.guzzing.studayserver.domain.like.service.dto.response.LikeGetResult;
import org.guzzing.studayserver.domain.like.service.dto.response.LikePostResult;
import org.guzzing.studayserver.domain.like.service.dto.response.LikedAcademyFeeInfo;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeFacade {

    private final LikeReadService likeReadService;
    private final LikeCommandService likeCommandService;
    private final MemberAccessService memberAccessService;
    private final AcademyAccessService academyAccessService;

    public LikeFacade(
            final LikeReadService likeReadService,
            final LikeCommandService likeCommandService,
            final MemberAccessService memberAccessService,
            final AcademyAccessService academyAccessService
    ) {
        this.likeReadService = likeReadService;
        this.likeCommandService = likeCommandService;
        this.memberAccessService = memberAccessService;
        this.academyAccessService = academyAccessService;
    }

    @Transactional
    public LikePostResult createLikeOfAcademy(final LikePostParam param) {
        memberAccessService.validateMember(param.memberId());
        academyAccessService.validateAcademy(param.academyId());

        validateLikeCreation(param);

        final Like savedLike = likeCommandService.saveLike(param.memberId(), param.academyId());

        return LikePostResult.from(savedLike);
    }

    @Transactional
    public void removeLike(final long likeId, final long memberId) {
        memberAccessService.validateMember(memberId);

        likeCommandService.deleteLike(likeId);
    }

    @Transactional
    public void deleteLikeOfAcademy(final long academyId, final long memberId) {
        memberAccessService.validateMember(memberId);
        academyAccessService.validateAcademy(academyId);

        likeCommandService.deleteLikesOfAcademyAndMember(academyId, memberId);
    }

    @Transactional(readOnly = true)
    public boolean isLiked(final long academyId, final long memberId) {
        memberAccessService.validateMember(memberId);
        academyAccessService.validateAcademy(academyId);

        return likeReadService.isLikedAcademy(academyId, memberId);
    }

    @Transactional(readOnly = true)
    public LikeGetResult getAllLikesOfMember(final long memberId) {
        memberAccessService.validateMember(memberId);

        final List<LikedAcademyFeeInfo> likedAcademyFeeInfos = likeReadService.findAllLikesOfMember(memberId).stream()
                .map(like -> {
                    final AcademyFeeInfo academyFeeInfo = academyAccessService.findAcademyFeeInfo(like.getAcademyId());

                    return new LikedAcademyFeeInfo(
                            like.getId(),
                            like.getAcademyId(),
                            academyFeeInfo.academyName(),
                            academyFeeInfo.expectedFee());
                })
                .toList();

        return LikeGetResult.of(likedAcademyFeeInfos);
    }

    private void validateLikeCreation(final LikePostParam param) {
        likeReadService.validateLikeLimit(param);
        likeReadService.validateExistsLike(param);
    }

}
