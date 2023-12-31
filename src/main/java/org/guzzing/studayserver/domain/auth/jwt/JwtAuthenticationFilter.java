package org.guzzing.studayserver.domain.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final AuthTokenProvider tokenProvider;

    public JwtAuthenticationFilter(AuthTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String AUTHORIZATION_HEADER = request.getHeader(HEADER_AUTHORIZATION);

        if (AUTHORIZATION_HEADER != null && AUTHORIZATION_HEADER.startsWith(TOKEN_PREFIX)) {
            String tokenStr = JwtHeaderUtil.getAccessToken(request);
            AuthToken token = tokenProvider.convertAuthToken(tokenStr);

            if (token.isValidTokenClaims()) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

}
