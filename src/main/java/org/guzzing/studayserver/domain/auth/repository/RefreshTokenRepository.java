package org.guzzing.studayserver.domain.auth.repository;


import java.util.Optional;
import org.guzzing.studayserver.domain.auth.jwt.JwtTokenCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<JwtTokenCache, Long> {

    Optional<JwtTokenCache> findByAccessToken(String accessToken);

}
