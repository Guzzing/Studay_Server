package org.guzzing.studayserver.domain.auth.jwt;


import jakarta.servlet.http.HttpServletRequest;

public class JwtHeaderUtil {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private JwtHeaderUtil() {
    }

    public static String getAccessToken(HttpServletRequest request) {

        String httpHeaderAuthorizationString = request.getHeader(HEADER_AUTHORIZATION);

        if (httpHeaderAuthorizationString == null ||
                !httpHeaderAuthorizationString.startsWith(TOKEN_PREFIX)) {
            return null;
        }

        return httpHeaderAuthorizationString.substring(TOKEN_PREFIX.length());
    }

}
