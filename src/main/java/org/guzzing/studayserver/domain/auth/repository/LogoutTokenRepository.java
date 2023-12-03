package org.guzzing.studayserver.domain.auth.repository;

import com.github.benmanes.caffeine.cache.Cache;
import org.guzzing.studayserver.domain.auth.config.JwtTokenCacheConfig;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LogoutTokenRepository {

    private final JwtTokenCacheConfig jwtTokenCacheConfig;
    private final Cache<String, String> logoutCache;

    public LogoutTokenRepository(JwtTokenCacheConfig jwtTokenCacheConfig) {
        this.jwtTokenCacheConfig = jwtTokenCacheConfig;
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
