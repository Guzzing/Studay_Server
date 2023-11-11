package org.guzzing.studayserver.domain.auth.repository;

import org.guzzing.studayserver.domain.auth.jwt.logout.LogoutCache;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LogoutTokenRepository extends CrudRepository<LogoutCache, String> {

    Optional<LogoutCache> findById(String accessToken);
}
