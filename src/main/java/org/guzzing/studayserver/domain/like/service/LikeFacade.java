package org.guzzing.studayserver.domain.like.service;

import java.util.List;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.service.AcademyService;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyFeeInfo;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.service.dto.request.LikePostParam;
import org.guzzing.studayserver.domain.like.service.dto.response.LikeGetResult;
import org.guzzing.studayserver.domain.like.service.dto.response.LikePostResult;
import org.guzzing.studayserver.domain.like.service.dto.response.LikedAcademyFeeInfo;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeFacade {

    private final LikeReadService likeReadService;
    private final LikeCommandService likeCommandService;
    private final MemberService memberService;
    private final AcademyService academyService;

    public LikeFacade(
            final LikeReadService likeReadService,
            final LikeCommandService likeCommandService,
            final MemberService memberService,
            final AcademyService academyService
    ) {
        this.likeReadService = likeReadService;
        this.likeCommandService = likeCommandService;
        this.memberService = memberService;
        this.academyService = academyService;
    }

    @Transactional
    public LikePostResult createLikeOfAcademy(final LikePostParam param) {
        final Member member = getValidMember(param.memberId());
        final Academy academy = getValidAcademy(param.academyId(), member);

        final Like savedLike = likeCommandService.saveLike(member, academy);

        return LikePostResult.from(savedLike);
    }

    @Transactional
    public void removeLike(final Long likeId, final Long memberId) {
        getValidMember(memberId);

        likeCommandService.deleteLike(likeId);
    }

    @Transactional
    public void deleteLikeOfAcademy(final Long memberId, final Long academyId) {
        final Member member = getValidMember(memberId);
        final Academy academy = academyService.findAcademy(academyId);

        likeCommandService.deleteLikesOfAcademyAndMember(member, academy);
    }

    @Transactional(readOnly = true)
    public boolean isLiked(final Long memberId, final Long academyId) {
        final Member member = memberService.getMember(memberId);
        final Academy academy = academyService.findAcademy(academyId);

        return likeReadService.isLikedAcademy(member, academy);
    }

    @Transactional(readOnly = true)
    public LikeGetResult getAllLikesOfMember(final Long memberId) {
        final Member member = getValidMember(memberId);

        final List<LikedAcademyFeeInfo> likedAcademyFeeInfos = likeReadService.findAllLikesOfMember(member)
                .stream()
                .map(like -> {
                    final long academyId = like.getAcademyId();

                    final AcademyFeeInfo academyFeeInfo = academyService.findAcademyFeeInfo(academyId);

                    return new LikedAcademyFeeInfo(
                            like.getId(),
                            academyId,
                            academyFeeInfo.academyName(),
                            academyFeeInfo.expectedFee());
                })
                .toList();

        return LikeGetResult.of(likedAcademyFeeInfos);
    }

    private Member getValidMember(final Long memberId) {
        final Member member = memberService.getMember(memberId);
        likeReadService.validateLikeLimit(member);
        return member;
    }

    private Academy getValidAcademy(final Long academyId, final Member member) {
        final Academy academy = academyService.findAcademy(academyId);
        likeReadService.validateExistsLike(member, academy);
        return academy;
    }

}
