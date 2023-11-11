package org.guzzing.studayserver.domain.auth.repository;

import org.guzzing.studayserver.domain.auth.jwt.logout.LogoutToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LogoutTokenRepository extends CrudRepository<LogoutToken, String> {

    Optional<LogoutToken> findById(String accessToken);
}
