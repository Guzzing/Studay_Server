package org.guzzing.studayserver.domain.auth.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class JwtTokenCacheConfig {

    private static final int REFRESH_TOKEN_EXPIRED_DURATION = 30;
    private static final int ACCESS_TOKEN_EXPIRED_DURATION = 1;
    private static final int MAXIMUM_CACHE_SIZE = 10000;

    @Bean
    public Cache<String, String> refreshCacheConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(REFRESH_TOKEN_EXPIRED_DURATION, TimeUnit.DAYS)
                .maximumSize(MAXIMUM_CACHE_SIZE)
                .build();
    }

    @Bean
    public Cache<String, String> logoutCacheConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(ACCESS_TOKEN_EXPIRED_DURATION, TimeUnit.DAYS)
                .maximumSize(MAXIMUM_CACHE_SIZE)
                .build();
    }

}
