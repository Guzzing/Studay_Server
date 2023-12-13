package org.guzzing.studayserver.domain.review.repository;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long>, ReviewRepository {

    boolean existsByMemberAndAcademy(final Member member, final Academy academy);

    void deleteByMember(final Member member);

}
