package org.guzzing.studayserver.domain.auth.jwt;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "JwtTokenCache", timeToLive = 60 * 60 * 24 * 7)
public class JwtTokenCache {

    @Id
    private Long memberId;

    private String refreshToken;

    @Indexed
    private String accessToken;

    public JwtTokenCache(Long memberId, String refreshToken, String accessToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

}
