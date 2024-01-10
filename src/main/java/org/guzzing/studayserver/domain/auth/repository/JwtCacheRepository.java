package org.guzzing.studayserver.domain.auth.repository;

import java.util.Optional;
import org.guzzing.studayserver.global.config.CaffeineCacheType;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;

@Repository
public class JwtCacheRepository {

    private final Cache cache;

    public JwtCacheRepository(CacheManager cacheManager) {
        this.cache = cacheManager.getCache(CaffeineCacheType.REFRESH_TOKEN.getCacheName());
    }

    public String save(String accessToken, String refreshToken) {
        cache.put(accessToken, refreshToken);
        return refreshToken;
    }

    public Optional<String> findRefreshToken(String accessToken) {
        String refreshToken = cache.get(accessToken, String.class);
        return Optional.ofNullable(refreshToken);
    }

    public void delete(String accessToken) {
        cache.evictIfPresent(accessToken);
    }

}
