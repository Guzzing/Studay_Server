package org.guzzing.studayserver.testutil;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    private static final String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzMTQ0OTg2NTgzIiwicm9sZSI6IlJPTEVfVVNFUiIsIm1lbWJlcklkIjoyLCJleHAiOjE2OTg5MjQ4OTJ9.mOJLR9VBMtB0xTcgiL9jnCfFdaygBTGRHOSX0TxVWcw";

    public String getJwtToken() {
        return jwtToken;
    }

}
