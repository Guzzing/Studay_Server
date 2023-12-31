package org.guzzing.studayserver.testutil;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "test")
public class JwtTestConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";

    private String jwt;

    public String getJwt() {
        return BEARER + jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
