package org.guzzing.studayserver.domain.like.service;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.academy.service.dto.result.AcademyFeeInfo;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.domain.like.service.dto.request.LikePostParam;
import org.guzzing.studayserver.domain.like.service.dto.response.LikeGetResult;
import org.guzzing.studayserver.domain.like.service.dto.response.LikePostResult;
import org.guzzing.studayserver.domain.like.service.dto.response.LikedAcademyFeeInfo;
import org.guzzing.studayserver.domain.member.annotation.ValidMember;
import org.guzzing.studayserver.domain.member.annotation.ValidatedMemberId;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.global.exception.LikeException;
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
        memberAccessService.validateMember(param.memberId());
        academyAccessService.validateAcademy(param.academyId());

        validateLikeLimit(param);
        validateExistsLike(param);

        final Like savedLike = likeRepository.save(
                Like.of(param.memberId(), param.academyId()));

        return LikePostResult.from(savedLike);
    }

    @Transactional
    public void removeLike(final Long likeId, final Long memberId) {
        memberAccessService.validateMember(memberId);

        likeRepository.deleteById(likeId);
    }

    @Transactional
    public void removeLike(final long memberId) {
        likeRepository.deleteByMemberId(memberId);
    }

    @ValidMember
    @Transactional
    public void deleteLikeOfAcademy(final Long academyId, @ValidatedMemberId final Long memberId) {
        academyAccessService.validateAcademy(academyId);

        likeRepository.deleteByAcademyIdAndMemberId(academyId, memberId);
    }

    public LikeGetResult findAllLikesOfMember(Long memberId) {
        memberAccessService.validateMember(memberId);

        final List<Like> likes = likeRepository.findByMemberId(memberId);

        final List<LikedAcademyFeeInfo> likeAcademyFeeInfos = likes.stream()
                .map(like -> {
                    AcademyFeeInfo academyFeeInfo = academyAccessService.findAcademyFeeInfo(like.getAcademyId());

                    return new LikedAcademyFeeInfo(
                            like.getId(),
                            like.getAcademyId(),
                            academyFeeInfo.academyName(),
                            academyFeeInfo.expectedFee());
                })
                .toList();

        final long totalFee = likeAcademyFeeInfos.stream()
                .mapToLong(LikedAcademyFeeInfo::expectedFee)
                .sum();

        return LikeGetResult.of(likeAcademyFeeInfos, totalFee);
    }

    private void validateLikeLimit(LikePostParam param) {
        final long likeCount = likeRepository.countByMemberId(param.memberId());

        if (likeCount >= 10) {
            throw new LikeException("좋아요 개수는 10개를 넘을 수 없습니다.");
        }
    }

    private void validateExistsLike(LikePostParam param) {
        boolean existsLike = likeRepository.existsByMemberIdAndAcademyId(param.memberId(), param.academyId());

        if (existsLike) {
            throw new LikeException("이미 좋아요한 학원입니다.");
        }
    }

}
