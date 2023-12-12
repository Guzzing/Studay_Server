package org.guzzing.studayserver.domain.review.service;

import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.domain.review.model.Review;
import org.guzzing.studayserver.domain.review.model.ReviewType;
import org.guzzing.studayserver.domain.review.service.dto.request.ReviewPostParam;
import org.guzzing.studayserver.domain.review.service.dto.response.ReviewPostResult;
import org.guzzing.studayserver.domain.review.service.dto.response.ReviewableResult;
import org.guzzing.studayserver.global.exception.ReviewException;
import org.springframework.stereotype.Service;

@Service
public class ReviewFacade {

    private final ReviewCommandService reviewCommandService;
    private final ReviewReadService reviewReadService;
    private final AcademyAccessService academyAccessService;
    private final MemberAccessService memberAccessService;

    public ReviewFacade(
            final ReviewCommandService reviewCommandService,
            final ReviewReadService reviewReadService,
            final AcademyAccessService academyAccessService,
            final MemberAccessService memberAccessService
    ) {
        this.reviewCommandService = reviewCommandService;
        this.reviewReadService = reviewReadService;
        this.academyAccessService = academyAccessService;
        this.memberAccessService = memberAccessService;
    }

    public ReviewPostResult createReviewOfAcademy(final ReviewPostParam param) {
        memberAccessService.validateMember(param.memberId());
        academyAccessService.validateAcademy(param.academyId());

        final ReviewableResult reviewableResult = getReviewableToAcademy(param.memberId(), param.academyId());

        if (!reviewableResult.reviewable()) {
            throw new ReviewException("이미 리뷰를 남겼습니다.");
        }

        final Review review = Review.of(
                param.memberId(),
                param.academyId(),
                ReviewType.getSelectedReviewMap(param));

        final Review savedReview = reviewCommandService.saveReview(review);

        return ReviewPostResult.from(savedReview);
    }

    public void removeReview(final long memberId) {
        reviewCommandService.deleteReviewOfMember(memberId);
    }

    public ReviewableResult getReviewableToAcademy(final Long memberId, final Long academyId) {
        memberAccessService.validateMember(memberId);
        academyAccessService.validateAcademy(academyId);

        boolean existsReview = reviewReadService.existsReview(memberId, academyId);

        return ReviewableResult.of(memberId, academyId, !existsReview);
    }

}
