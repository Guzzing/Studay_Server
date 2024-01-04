package org.guzzing.studayserver.domain.review.repository;

import org.guzzing.studayserver.domain.academy.model.Academy;
import org.guzzing.studayserver.domain.member.model.Member;
import org.guzzing.studayserver.domain.review.model.Review;

public interface ReviewRepository {

    Review save(final Review review);

    boolean existsByMemberAndAcademy(final Member member, final Academy academy);

}
