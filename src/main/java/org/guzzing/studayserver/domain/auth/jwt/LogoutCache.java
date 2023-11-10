package org.guzzing.studayserver.domain.auth.jwt;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "LogoutCache", timeToLive = 24 * 60 * 60)
public class LogoutCache {

    @Id
    private Long memberId;

    @Indexed
    private String accessToken;

    protected LogoutCache(Long memberId, String accessToken) {
        this.memberId = memberId;
        this.accessToken = accessToken;
    }

    public static LogoutCache of(Long memberId, String accessToken) {
        return new LogoutCache(memberId, accessToken);
    }

}
