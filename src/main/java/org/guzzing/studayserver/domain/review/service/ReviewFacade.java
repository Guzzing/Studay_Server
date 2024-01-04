package org.guzzing.studayserver.domain.review.service;

import jakarta.persistence.EntityExistsException;
import java.util.Map;
import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.academy.service.AcademyService;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.member.service.MemberService;
import org.guzzing.studayserver.domain.review.model.Review;
import org.guzzing.studayserver.domain.review.model.ReviewType;
import org.guzzing.studayserver.domain.review.service.dto.request.ReviewPostParam;
import org.guzzing.studayserver.domain.review.service.dto.response.ReviewPostResult;
import org.guzzing.studayserver.domain.review.service.dto.response.ReviewableResult;
import org.springframework.stereotype.Service;

@Service
public class ReviewFacade {

    private final ReviewCommandService reviewCommandService;
    private final ReviewReadService reviewReadService;
    private final MemberService memberService;
    private final AcademyService academyService;

    public ReviewFacade(
            final ReviewCommandService reviewCommandService,
            final ReviewReadService reviewReadService,
            final MemberService memberService,
            final AcademyService academyService
    ) {
        this.reviewCommandService = reviewCommandService;
        this.reviewReadService = reviewReadService;
        this.memberService = memberService;
        this.academyService = academyService;
    }

    public ReviewPostResult createReviewOfAcademy(final ReviewPostParam param) {
        final Member member = memberService.getMember(param.memberId());
        final Academy academy = academyService.findAcademy(param.academyId());

        checkReviewExists(member, academy);

        final Map<ReviewType, Boolean> selectedReviewMap = ReviewType.getSelectedReviewMap(param);

        final Review savedReview = reviewCommandService.saveReview(
                Review.of(member, academy, selectedReviewMap));
        academyService.getReviewCountOfAcademy(academy.getId())
                .updateSelectedReviewCount(selectedReviewMap);

        return ReviewPostResult.from(savedReview);
    }

    public ReviewableResult getReviewableToAcademy(final long memberId, final long academyId) {
        final Member member = memberService.getMember(memberId);
        final Academy academy = academyService.findAcademy(academyId);

        final boolean existsReview = reviewReadService.existsReview(member, academy);

        return ReviewableResult.of(member.getId(), academy.getId(), !existsReview);
    }

    private void checkReviewExists(Member member, Academy academy) {
        final boolean existsReview = reviewReadService.existsReview(member, academy);

        if (existsReview) {
            throw new EntityExistsException("이미 리뷰를 남겼습니다.");
        }
    }

}
