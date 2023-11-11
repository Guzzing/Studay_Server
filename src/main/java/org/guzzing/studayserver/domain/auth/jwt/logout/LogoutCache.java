package org.guzzing.studayserver.domain.auth.jwt.logout;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "LogoutCache", timeToLive = 24 * 60 * 60)
public class LogoutCache {

    @Id
    private String accessToken;

    protected LogoutCache(String accessToken) {
        this.accessToken = accessToken;
    }

    public static LogoutCache of(String accessToken) {
        return new LogoutCache(accessToken);
    }

}
