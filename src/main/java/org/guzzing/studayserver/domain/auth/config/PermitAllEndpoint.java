package org.guzzing.studayserver.domain.auth.config;

public class PermitAllEndpoint {

    private PermitAllEndpoint() {
    }

    protected static final String[] permitAllArray = new String[]{
            "/",
            "/auth/**",
            "/error",
            "/docs/**",
            "/favicon.ico",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/actuator/**",
            "/metrics/**"
    };
}
