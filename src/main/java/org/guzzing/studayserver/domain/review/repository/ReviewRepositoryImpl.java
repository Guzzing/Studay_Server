package org.guzzing.studayserver.domain.review.repository;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.review.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository{

    private final ReviewJpaRepository reviewJpaRepository;

    public ReviewRepositoryImpl(ReviewJpaRepository reviewJpaRepository) {
        this.reviewJpaRepository = reviewJpaRepository;
    }

    @Override
    public Review save(Review review) {
        return reviewJpaRepository.save(review);
    }

    @Override
    public boolean existsByMemberAndAcademy(Member member, Academy academy) {
        return reviewJpaRepository.existsByMemberAndAcademy(member, academy);
    }

    @Override
    public void deleteByMember(Member member) {
        reviewJpaRepository.deleteByMember(member);
    }
}
