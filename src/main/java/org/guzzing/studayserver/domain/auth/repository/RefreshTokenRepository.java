package org.guzzing.studayserver.domain.auth.repository;

import java.util.Optional;

import org.guzzing.studayserver.domain.auth.jwt.JwtToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<JwtToken, String> {

    Optional<JwtToken> findById(String accessToken);

}
