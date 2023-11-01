package org.guzzing.studayserver.domain.auth.repository;


import org.guzzing.studayserver.domain.auth.jwt.JwtTokenCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<JwtTokenCache, String> {

    Optional<JwtTokenCache> findByAccessToken(String accessToken);

}
