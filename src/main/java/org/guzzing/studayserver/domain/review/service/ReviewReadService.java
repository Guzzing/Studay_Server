package org.guzzing.studayserver.domain.review.service;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReviewReadService {

    private final ReviewRepository reviewRepository;

    public ReviewReadService(final ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public boolean existsReview(final Member member, final Academy academy) {
        return reviewRepository.existsByMemberAndAcademy(member, academy);
    }

}
