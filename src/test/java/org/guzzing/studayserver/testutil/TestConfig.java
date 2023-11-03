package org.guzzing.studayserver.testutil;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "test")
public class TestConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";

    private String jwt;

    public String getJwt() {
        return this.jwt;
    }

    public void setJwt(final String jwt) {
        this.jwt = jwt;
    }

}
