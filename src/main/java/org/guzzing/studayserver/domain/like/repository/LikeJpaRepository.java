package org.guzzing.studayserver.domain.like.repository;

import org.guzzing.studayserver.domain.like.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepository extends JpaRepository<Like, Long>, LikeRepository {

}
