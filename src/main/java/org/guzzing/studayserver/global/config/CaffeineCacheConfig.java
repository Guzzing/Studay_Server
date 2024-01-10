package org.guzzing.studayserver.global.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CaffeineCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<CaffeineCache> caches = Arrays.stream(CaffeineCacheType.values())
                .map(caffeineCacheType -> new CaffeineCache(
                        caffeineCacheType.getCacheName(),
                        Caffeine.newBuilder()
                                .expireAfterWrite(caffeineCacheType.getExpireAfterWrite(), TimeUnit.DAYS)
                                .maximumSize(caffeineCacheType.getMaximumSize())
                                .build()
                ))
                .toList();

        cacheManager.setCaches(caches);

        return cacheManager;
    }

}
