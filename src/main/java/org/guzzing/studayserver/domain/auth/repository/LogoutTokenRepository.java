package org.guzzing.studayserver.domain.auth.repository;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.Optional;
import org.guzzing.studayserver.domain.auth.config.JwtTokenCacheConfig;
import org.springframework.stereotype.Repository;

@Repository
public class LogoutTokenRepository {

    private final Cache<String, String> logoutCache;

    public LogoutTokenRepository(JwtTokenCacheConfig jwtTokenCacheConfig) {
        this.logoutCache = jwtTokenCacheConfig.logoutCacheConfig();
    }

    public String save(String logoutToken) {
        logoutCache.put(logoutToken, logoutToken);
        return logoutToken;
    }

    public Optional<String> findByLogoutToken(String logoutToken) {
        return Optional.ofNullable(logoutCache.getIfPresent(logoutToken));
    }

    public void delete(String logoutToken) {
        logoutCache.invalidate(logoutToken);
    }

}
