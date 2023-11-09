package org.guzzing.studayserver.domain.academy.repository.review;

import org.guzzing.studayserver.domain.academy.model.ReviewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewCountJpaRepository extends JpaRepository<ReviewCount, Long>, ReviewCountRepository {

    @Query("select rc from ReviewCount rc where rc.academy.id =:academyId")
    ReviewCount getByAcademyId(Long academyId);

}
