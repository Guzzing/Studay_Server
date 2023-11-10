package org.guzzing.studayserver.domain.auth.repository;

import org.guzzing.studayserver.domain.auth.jwt.LogoutCache;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LogoutTokenRepository  extends CrudRepository<LogoutCache, Long> {

    Optional<LogoutCache> findByAccessToken(String accessToken);
}
