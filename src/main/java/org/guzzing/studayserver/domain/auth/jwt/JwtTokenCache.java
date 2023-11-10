package org.guzzing.studayserver.domain.auth.jwt;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "JwtTokenCache", timeToLive = 60 * 60 * 24 * 7)
public class JwtTokenCache {

    private String refreshToken;

    @Id
    private String accessToken;

    public JwtTokenCache(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

}
