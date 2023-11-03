package org.guzzing.studayserver.domain.like.repository;

import java.util.List;
import org.guzzing.studayserver.domain.like.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepository extends JpaRepository<Like, Long>, LikeRepository {

    List<Like> findByMemberId(final Long memberId);

    long countByMemberId(final Long memberId);

}
