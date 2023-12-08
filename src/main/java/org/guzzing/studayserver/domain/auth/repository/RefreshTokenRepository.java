package org.guzzing.studayserver.domain.auth.repository;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.guzzing.studayserver.domain.auth.config.JwtTokenCacheConfig;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenRepository {

    private final JwtTokenCacheConfig jwtTokenCacheConfig;
    private final Cache<String, String> refreshCache;

    public RefreshTokenRepository(JwtTokenCacheConfig jwtTokenCacheConfig) {
        this.jwtTokenCacheConfig = jwtTokenCacheConfig;
        this.refreshCache = jwtTokenCacheConfig.refreshCacheConfig();
    }

    public String save(String accessToken, String refreshToken) {
        refreshCache.put(accessToken, refreshToken);
        return refreshToken;
    }

    public Optional<String> findByAccessToken(String accessToken) {
        return Optional.ofNullable(refreshCache.getIfPresent(accessToken));
    }

    public List<Map.Entry<String, String>> findAll() {
        return refreshCache.asMap()
                .entrySet()
                .stream()
                .toList();
    }

    public void deleteByAccessToken(String accessToken) {
        refreshCache.invalidate(accessToken);
    }

}
