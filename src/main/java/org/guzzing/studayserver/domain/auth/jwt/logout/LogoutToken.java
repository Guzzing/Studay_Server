package org.guzzing.studayserver.domain.auth.jwt.logout;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "LogoutCache", timeToLive = 24 * 60 * 60)
public class LogoutToken {

    @Id
    private String accessToken;

    protected LogoutToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static LogoutToken of(String accessToken) {
        return new LogoutToken(accessToken);
    }

}
