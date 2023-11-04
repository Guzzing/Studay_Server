package org.guzzing.studayserver.domain.review.service;

import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.domain.review.model.Review;
import org.guzzing.studayserver.domain.review.model.ReviewType;
import org.guzzing.studayserver.domain.review.repository.ReviewRepository;
import org.guzzing.studayserver.domain.review.service.dto.ReviewPostParam;
import org.guzzing.studayserver.domain.review.service.dto.ReviewPostResult;
import org.guzzing.studayserver.global.exception.AcademyException;
import org.guzzing.studayserver.global.exception.MemberException;
import org.guzzing.studayserver.global.exception.ReviewException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AcademyAccessService academyAccessService;
    private final MemberAccessService memberAccessService;

    public ReviewService(
            final ReviewRepository reviewRepository,
            final AcademyAccessService academyAccessService,
            final MemberAccessService memberAccessService
    ) {
        this.reviewRepository = reviewRepository;
        this.academyAccessService = academyAccessService;
        this.memberAccessService = memberAccessService;
    }

    public ReviewPostResult createReviewOfAcademy(final ReviewPostParam reviewPostParam) {
        validateMember(reviewPostParam.memberId());
        validateAcademy(reviewPostParam.academyId());

        validateReviewedYet(reviewPostParam);

        final Review review = Review.of(
                reviewPostParam.academyId(),
                reviewPostParam.memberId(),
                ReviewType.getSelectedReviewMap(reviewPostParam));

        Review savedReview = reviewRepository.save(review);

        return ReviewPostResult.from(savedReview);
    }

    private void validateReviewedYet(ReviewPostParam reviewPostParam) {
        boolean existsReview = reviewRepository.existsByMemberIdAndAcademyId(
                reviewPostParam.memberId(),
                reviewPostParam.academyId());

        if (existsReview) {
            throw new ReviewException("이미 리뷰를 남겼습니다.");
        }
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
