package org.guzzing.studayserver.domain.like.service;

import java.util.List;
import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.like.model.Like;
import org.guzzing.studayserver.domain.like.repository.LikeRepository;
import org.guzzing.studayserver.domain.like.service.dto.request.LikePostParam;
import org.guzzing.studayserver.domain.like.service.dto.response.AcademyFeeInfo;
import org.guzzing.studayserver.domain.like.service.dto.response.LikeGetResult;
import org.guzzing.studayserver.domain.like.service.dto.response.LikePostResult;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.global.exception.AcademyException;
import org.guzzing.studayserver.global.exception.LikeException;
import org.guzzing.studayserver.global.exception.MemberException;
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
        validateMember(param.memberId());
        validateAcademy(param.academyId());

        long likeCount = likeRepository.countByMemberId(param.memberId());

        if (likeCount >= 10) {
            throw new LikeException("좋아요 개수는 10개를 넘을 수 없습니다.");
        }

        Like savedLike = likeRepository.save(
                Like.of(param.memberId(), param.academyId()));

        return LikePostResult.from(savedLike);
    }

    public void removeLikeOfAcademy(final Long likeId, final Long memberId) {
        validateMember(memberId);

        likeRepository.deleteById(likeId);
    }

    public LikeGetResult findAllLikesOfMember(Long memberId) {
        List<Like> likes = likeRepository.findByMemberId(memberId);

        List<AcademyFeeInfo> academyFeeInfos = likes.stream()
                .map(like -> academyAccessService.findAcademyFeeInfo(like.getAcademyId()))
                .toList();

        long totalFee = academyFeeInfos.stream()
                .mapToLong(AcademyFeeInfo::expectedFee)
                .sum();

        return LikeGetResult.of(academyFeeInfos, totalFee);
    }

    private void validateMember(final Long memberId) {
        boolean isExistMember = memberAccessService.existsMember(memberId);

        if (!isExistMember) {
            throw new MemberException("존재하지 않는 멤버입니다.");
        }
    }

    private void validateAcademy(final Long academyId) {
        boolean isExistAcademy = academyAccessService.existsAcademy(academyId);

        if (!isExistAcademy) {
            throw new AcademyException("존재하지 않는 학원입니다.");
        }
    }
}
