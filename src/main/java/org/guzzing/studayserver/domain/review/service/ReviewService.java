package org.guzzing.studayserver.domain.review.service;

import org.guzzing.studayserver.domain.academy.service.AcademyAccessService;
import org.guzzing.studayserver.domain.member.service.MemberAccessService;
import org.guzzing.studayserver.domain.review.event.NewReviewEvent;
import org.guzzing.studayserver.domain.review.model.Review;
import org.guzzing.studayserver.domain.review.model.ReviewType;
import org.guzzing.studayserver.domain.review.repository.ReviewRepository;
import org.guzzing.studayserver.domain.review.service.dto.request.ReviewPostParam;
import org.guzzing.studayserver.domain.review.service.dto.response.ReviewPostResult;
import org.guzzing.studayserver.domain.review.service.dto.response.ReviewableResult;
import org.guzzing.studayserver.global.exception.ReviewException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ApplicationEventPublisher eventPublisher;
    private final ReviewRepository reviewRepository;
    private final AcademyAccessService academyAccessService;
    private final MemberAccessService memberAccessService;

    public ReviewService(
            final ApplicationEventPublisher eventPublisher,
            final ReviewRepository reviewRepository,
            final AcademyAccessService academyAccessService,
            final MemberAccessService memberAccessService
    ) {
        this.eventPublisher = eventPublisher;
        this.reviewRepository = reviewRepository;
        this.academyAccessService = academyAccessService;
        this.memberAccessService = memberAccessService;
    }

    @Transactional
    public ReviewPostResult createReviewOfAcademy(final ReviewPostParam param) {
        memberAccessService.validateMember(param.memberId());
        academyAccessService.validateAcademy(param.academyId());

        final ReviewableResult reviewableResult = getReviewableToAcademy(
                param.memberId(),
                param.academyId());

        if (!reviewableResult.reviewable()) {
            throw new ReviewException("이미 리뷰를 남겼습니다.");
        }

        final Review review = Review.of(
                param.academyId(),
                param.memberId(),
                ReviewType.getSelectedReviewMap(param));

        final Review savedReview = reviewRepository.save(review);

        eventPublisher.publishEvent(NewReviewEvent.of(savedReview));

        return ReviewPostResult.from(savedReview);
    }

    public ReviewableResult getReviewableToAcademy(final Long memberId, final Long academyId) {
        memberAccessService.validateMember(memberId);
        academyAccessService.validateAcademy(academyId);

        final boolean existsReview = reviewRepository.existsByMemberIdAndAcademyId(memberId, academyId);

        return ReviewableResult.of(memberId, academyId, !existsReview);
    }

}
