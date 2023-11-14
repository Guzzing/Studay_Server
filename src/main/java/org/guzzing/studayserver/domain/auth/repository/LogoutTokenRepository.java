package org.guzzing.studayserver.domain.auth.repository;

import java.util.Optional;
import org.guzzing.studayserver.domain.auth.jwt.logout.LogoutToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutTokenRepository extends CrudRepository<LogoutToken, String> {

    Optional<LogoutToken> findById(String accessToken);
}
